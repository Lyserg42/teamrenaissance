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


public class LoanServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //gets the loans of a user per tournaments
        //gets the userID
        //TODO: use the sessions
        /*
        HttpSession session = req.getSession(false);
        if(session == null){
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ServletUtils.CONNECTION_NEEDED_MESSAGE);
            return;
        }
        User user = (User)session.getAttribute("user");
        Integer userId = user.getUserID();
        */
        Integer userId = 1;

        //gets tournaments in chronological order
        List<Tournament> tournaments = TournamentManager.getAllTournaments();

        //create the JSON
        try {
            JSONObject resultJson = new JSONObject();
            JSONArray tournamentsArray = new JSONArray();

            for (Tournament tournament : tournaments) {

                JSONArray lentCards = LoanManager.getLentCardsJson(userId, tournament.getTournamentID());
                JSONArray borrowedCards = LoanManager.getBorrowedCardsJson(userId, tournament.getTournamentID());
                JSONArray demands = LoanManager.getDemandsJson(userId, tournament.getTournamentID());

                if (lentCards.length() != 0 || borrowedCards.length() != 0 || demands.length() != 0) {
                    JSONObject tournamentJson = new JSONObject();

                    tournamentJson.put("tID", tournament.getTournamentID());
                    tournamentJson.put("tName", tournament.getName());
                    tournamentJson.put("date", tournament.getDate());

                    tournamentJson.put("lentCards", lentCards);
                    tournamentJson.put("borrowedCards", borrowedCards);
                    tournamentJson.put("demands", demands);

                    tournamentsArray.put(tournamentJson);
                }
            }

            resultJson.put("tournaments", tournamentsArray);

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

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject newLoanJson = ServletUtils.getJsonFromRequest(req);

        //get the connected user
        //TODO: use the sessions
        /*
        HttpSession session = req.getSession(false);
        if(session == null){
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ServletUtils.CONNECTION_NEEDED_MESSAGE);
            return;
        }
        User user = (User)session.getAttribute("user");
        */
        User user = UserManager.getUser(1);

        //get the JSON information
        try {
            ArrayList<Loan> loans = new ArrayList<>();
            Tournament tournament = TournamentManager.getTournament(newLoanJson.getInt("tId"));
            JSONArray cardsArray = newLoanJson.getJSONArray("cards");
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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            JSONObject modifierPret = ServletUtils.getJsonFromRequest(req);
            Integer borrowerId, lenderId;
            boolean emprunt = modifierPret.getString("type").equals("emprunt");
            //TODO: use sessions
            Integer sessionUserId = 2;
            borrowerId = emprunt ? sessionUserId : modifierPret.getInt("uId");
            lenderId = emprunt ? modifierPret.getInt("uId") : sessionUserId;

            int tournamentID = modifierPret.getInt("tId");
            JSONArray cardsArray = modifierPret.getJSONArray("cards");
            for(int i = 0; i < cardsArray.length(); i++){
                JSONObject obj = cardsArray.getJSONObject(i);
                if(emprunt){
                    LoanManager.deleteLoans(borrowerId, lenderId, tournamentID, obj.getInt("cId"), obj.getInt("newQty"));
                } else {
                    LoanManager.deleteLender(borrowerId, lenderId, tournamentID, obj.getInt("cId"), obj.getInt("newQty"));
                }
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
