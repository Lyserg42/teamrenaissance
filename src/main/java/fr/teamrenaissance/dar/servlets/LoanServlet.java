package fr.teamrenaissance.dar.servlets;

import fr.teamrenaissance.dar.entities.Card;
import fr.teamrenaissance.dar.entities.Loan;
import fr.teamrenaissance.dar.entities.Tournament;
import fr.teamrenaissance.dar.entities.User;
import fr.teamrenaissance.dar.managers.CardManager;
import fr.teamrenaissance.dar.managers.LoanManager;
import fr.teamrenaissance.dar.managers.TournamentManager;
import fr.teamrenaissance.dar.managers.UserManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class LoanServlet extends HttpServlet {

    /**
     * request = "mesprets" : Récupérer les prêts, emprunts et demandes en cours d'un utilisateur.
     * request = "accueil" : Récupérer toutes les demandes de prêt en cours (pas de prêteur trouvé pour le moment).
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String request = req.getParameter("request");
        if(request == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            //create the JSON
            JSONObject resultJson = new JSONObject();
            //gets tournaments in chronological order
            List<Tournament> tournaments = TournamentManager.getAllTournaments();
            JSONArray tournamentsArray = new JSONArray();

            //gets the demands of all the users for each tournament
            if(request.equals("demandes")){
                //get the connected user if there is one
                HttpSession session = req.getSession(false);
                Optional<Integer> userId = Optional.empty();
                if(session != null){
                    JSONObject sessionJson = (JSONObject) session.getAttribute(UserServlet.USER);
                    userId = Optional.of(sessionJson.getInt("userId"));
                }

                for (Tournament tournament : tournaments) {
                    JSONObject tournamentJson = new JSONObject();
                    tournamentJson.put("tId", tournament.getTournamentID());
                    tournamentJson.put("tName", tournament.getName());
                    tournamentJson.put("date", tournament.getDate());

                    JSONArray demands = LoanManager.getAllDemandsJson(tournament.getTournamentID(), userId);
                    if(demands.length() == 0) continue;
                    tournamentJson.put("demandes", demands);
                    tournamentsArray.put(tournamentJson);
                }
            }

            //for the connected user, gets the lent cards, borrowed cards and demands for each tournament
            else if(request.equals("mesprets")){
                //gets the userID
                HttpSession session = req.getSession(false);
                if(session == null){
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ServletUtils.CONNECTION_NEEDED_MESSAGE);
                    return;
                }
                JSONObject sessionJson = (JSONObject) session.getAttribute(UserServlet.USER);
                Integer userId = sessionJson.getInt("userId");

                for (Tournament tournament : tournaments) {
                    JSONArray lentCards = LoanManager.getLentCardsJson(userId, tournament.getTournamentID());
                    JSONArray borrowedCards = LoanManager.getBorrowedCardsJson(userId, tournament.getTournamentID());
                    JSONArray demands = LoanManager.getDemandsJson(userId, tournament.getTournamentID());

                    if (lentCards.length() != 0 || borrowedCards.length() != 0 || demands.length() != 0) {
                        JSONObject tournamentJson = new JSONObject();

                        tournamentJson.put("tId", tournament.getTournamentID());
                        tournamentJson.put("tName", tournament.getName());
                        tournamentJson.put("date", tournament.getDate());

                        tournamentJson.put("lentCards", lentCards);
                        tournamentJson.put("borrowedCards", borrowedCards);
                        tournamentJson.put("demands", demands);

                        tournamentsArray.put(tournamentJson);
                    }
                }

            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            resultJson.put("tournaments", tournamentsArray);
            resp.setStatus(HttpServletResponse.SC_OK);

            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.print(resultJson);
            writer.flush();
            writer.close();

        } catch (JSONException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.	SC_INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    /**
     * Faire une nouvelle demande d'emprunt.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject nouvelleDemande = ServletUtils.getJsonFromRequest(req);
        try {
            //get the connected user
            HttpSession session = req.getSession(false);
            if(session == null){
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ServletUtils.CONNECTION_NEEDED_MESSAGE);
                return;
            }
            JSONObject sessionJson = (JSONObject) session.getAttribute(UserServlet.USER);
            Integer userId = sessionJson.getInt("userId");
            User user = UserManager.getUser(userId);

            //get the JSON information
            ArrayList<Loan> loans = new ArrayList<>();
            Tournament tournament = TournamentManager.getTournament(nouvelleDemande.getInt("tId"));
            JSONArray cardsArray = nouvelleDemande.getJSONArray("cards");
            JSONArray failed = new JSONArray();

            for (int i = 0; i < cardsArray.length(); i++) {
                JSONObject cardJson = (JSONObject) cardsArray.get(i);
                //get card
                Card card = CardManager.getCardByName(cardJson.getString("cName"));
                if(card == null){
                    failed.put(cardJson.getString("cName"));
                    continue;
                }
                //get quantity
                int qty = cardJson.getInt("qty");
                for(int j = 0; j < qty; j++){
                    loans.add(new Loan(tournament, card, user));
                }
            }

            if(failed.length() == 0){
                LoanManager.insertLoans(loans);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.setContentType("application/json");
                PrintWriter out = resp.getWriter();
                out.print(failed);
                out.flush();
            }

        } catch (JSONException e){
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * preter : une demande de prêt a reçu une réponse, ajouter le 'lender' au prêt.
     *
     * modifier : Annuler un prêt ou une demande d'emprunt.
     *
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String request = req.getParameter("request");
        if(request == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            if(request.equals("preter")) {
                //gets the connected user
                HttpSession session = req.getSession(false);
                if (session == null) {
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ServletUtils.CONNECTION_NEEDED_MESSAGE);
                    return;
                }
                JSONObject sessionJson = (JSONObject) session.getAttribute(UserServlet.USER);
                Integer lenderId = sessionJson.getInt("userId");
                User lender = UserManager.getUser(lenderId);

                JSONObject nouveauPret = ServletUtils.getJsonFromRequest(req);
                Integer borrowerId = nouveauPret.getInt("uId");

                int tournamentID = nouveauPret.getInt("tId");
                JSONArray cardsArray = nouveauPret.getJSONArray("cards");
                for (int i = 0; i < cardsArray.length(); i++) {
                    JSONObject obj = cardsArray.getJSONObject(i);
                    LoanManager.setLender(borrowerId, lenderId, tournamentID, obj.getInt("cId"), obj.getInt("qty"));
                }
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            else if(request.equals("modifier")){
                //gets the connected user
                HttpSession session = req.getSession(false);
                if(session == null){
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ServletUtils.CONNECTION_NEEDED_MESSAGE);
                    return;
                }
                JSONObject sessionJson = (JSONObject) session.getAttribute(UserServlet.USER);
                Integer sessionUserId = sessionJson.getInt("userId");

                JSONObject modifierPret = ServletUtils.getJsonFromRequest(req);
                Integer borrowerId, lenderId;
                boolean pret = modifierPret.getString("type").equals("pret");

                Integer uId = null;
                if(modifierPret.has("uId")){
                    uId = modifierPret.getInt("uId");
                }
                lenderId = pret ? sessionUserId : uId;
                borrowerId = pret ? uId : sessionUserId;

                int tournamentID = modifierPret.getInt("tId");
                JSONArray cardsArray = modifierPret.getJSONArray("cards");
                for(int i = 0; i < cardsArray.length(); i++){
                    JSONObject obj = cardsArray.getJSONObject(i);
                    if(pret){
                        LoanManager.deleteLender(borrowerId, lenderId, tournamentID, obj.getInt("cId"), obj.getInt("qty"));
                    } else {
                        LoanManager.deleteLoans(borrowerId, lenderId, tournamentID, obj.getInt("cId"), obj.getInt("qty"));
                    }
                }
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
