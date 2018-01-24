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
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

//import org.apache.commons.mail.*;


public class LoanServlet extends HttpServlet {

    /**
     * request = "mesprets" : Récupérer les prêts, emprunts et demandes en cours d'un utilisateur.
     * request = "accueil" : Récupérer toutes les demandes de prêt en cours (pas de prêteur trouvé pour le moment).
     */
//    @Override
//    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String request = req.getParameter("request");
//        System.out.println("LoanServlet : Request GET "+request+" session : "+req.getCookies());
//        if(request == null){
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//        try {
//            //create the JSON
//            JSONObject resultJson = new JSONObject();
//            //gets tournaments in chronological order
//            List<Tournament> tournaments = TournamentManager.getAllTournaments();
//            JSONArray tournamentsArray = new JSONArray();
//
//            //gets the demands of all the users for each tournament
//            if(request.equals("demandes")){
//                //get the connected user if there is one
//                HttpSession session = req.getSession(false);
//                Optional<Integer> userId = Optional.empty();
//                if(session != null){
//                    JSONObject sessionJson = (JSONObject) session.getAttribute(UserServlet.USER);
//                    userId = Optional.of(sessionJson.getInt("userId"));
//                }
//
//                for (Tournament tournament : tournaments) {
//                    JSONObject tournamentJson = new JSONObject();
//                    tournamentJson.put("tId", tournament.getTournamentID());
//                    tournamentJson.put("tName", tournament.getName());
//                    tournamentJson.put("date", tournament.getDate());
//
//                    JSONArray demands = LoanManager.getAllDemandsJson(tournament.getTournamentID(), userId);
//                    if(demands.length() == 0) continue;
//                    tournamentJson.put("demandes", demands);
//                    tournamentsArray.put(tournamentJson);
//                }
//            }
//
//            //for the connected user, gets the lent cards, borrowed cards and demands for each tournament
//            else if(request.equals("mesprets")){
//                //gets the userID
//                HttpSession session = req.getSession(false);
//                if(session == null){
//                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ServletUtils.CONNECTION_NEEDED_MESSAGE);
//                    return;
//                }
//                JSONObject sessionJson = (JSONObject) session.getAttribute(UserServlet.USER);
//                Integer userId = sessionJson.getInt("userId");
//
//                for (Tournament tournament : tournaments) {
//                    JSONArray lentCards = LoanManager.getLentCardsJson(userId, tournament.getTournamentID());
//                    JSONArray borrowedCards = LoanManager.getBorrowedCardsJson(userId, tournament.getTournamentID());
//                    JSONArray demands = LoanManager.getDemandsJson(userId, tournament.getTournamentID());
//
//                    if (lentCards.length() != 0 || borrowedCards.length() != 0 || demands.length() != 0) {
//                        JSONObject tournamentJson = new JSONObject();
//
//                        tournamentJson.put("tId", tournament.getTournamentID());
//                        tournamentJson.put("tName", tournament.getName());
//                        tournamentJson.put("date", tournament.getDate());
//
//                        tournamentJson.put("lentCards", lentCards);
//                        tournamentJson.put("borrowedCards", borrowedCards);
//                        tournamentJson.put("demands", demands);
//
//                        tournamentsArray.put(tournamentJson);
//                    }
//                }
//
//            } else {
//                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                return;
//            }
//
//            resultJson.put("tournaments", tournamentsArray);
//            resp.setStatus(HttpServletResponse.SC_OK);
//
//            resp.setContentType("application/json");
//            PrintWriter writer = resp.getWriter();
//            writer.print(resultJson);
//            writer.flush();
//            writer.close();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            resp.sendError(HttpServletResponse.	SC_INTERNAL_SERVER_ERROR , e.getMessage());
//        }
//    }

    /**
     * Faire une nouvelle demande d'emprunt.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*System.out.println("_________________________________");
        System.out.println("LoanServlet");
        System.out.println(req.getMethod());
        Enumeration<String> headerNames = req.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println("Header Name - " + headerName + ", Value - " + req.getHeader(headerName));
        }
        Enumeration<String> params = req.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = params.nextElement();
            System.out.println("Parameter Name - "+paramName+", Value - "+req.getParameter(paramName));
        }*/



        try {
            JSONObject request =  ServletUtils.getJsonFromRequest(req);
            String typeRequest = request.getString("typeRequest");
            System.out.println("LoanServlet : Request POST "+request+" Cookies : "+req.getCookies());


            //create the JSON
            JSONObject resultJson = new JSONObject();
            //gets tournaments in chronological order
            List<Tournament> tournaments = TournamentManager.getAllTournaments();
            JSONArray tournamentsArray = new JSONArray();

            //gets the demands of all the users for each tournament
            if(typeRequest.equals("demandes")){
                //get the connected user if there is one
                HttpSession session = req.getSession(false);
                Optional<Integer> userId = Optional.empty();
                if(session != null){
                    JSONObject sessionJson = (JSONObject) session.getAttribute(UserServlet.USER);
                    userId = Optional.of(sessionJson.getInt("userId"));
                    System.out.println("Requete demandes : session ok");
                }
                else {
                    System.out.println("Requete demandes : session null");
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
                resultJson.put("tournaments", tournamentsArray);
                resp.setStatus(HttpServletResponse.SC_OK);

                resp.setContentType("application/json");
                PrintWriter writer = resp.getWriter();
                writer.print(resultJson);
                writer.flush();
                writer.close();
            }

            //for the connected user, gets the lent cards, borrowed cards and demands for each tournament
            else if(typeRequest.equals("mesprets")){
                //gets the userID
                HttpSession session = req.getSession(false);
                if(session == null){
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ServletUtils.CONNECTION_NEEDED_MESSAGE);
                    System.out.println("Requete mesPrets : session null");
                    return;
                }   else {
                    System.out.println("Requete mesPrets : session ok");
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
                resultJson.put("tournaments", tournamentsArray);
                resp.setStatus(HttpServletResponse.SC_OK);

                resp.setContentType("application/json");
                PrintWriter writer = resp.getWriter();
                writer.print(resultJson);
                writer.flush();
                writer.close();
            }
            else if(typeRequest.equals("nouvelleDemande")){
                //get the connected user
                HttpSession session = req.getSession(false);
                if(session == null){
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ServletUtils.CONNECTION_NEEDED_MESSAGE);
                    System.out.println("Requete nouvelleDemandes : session null");
                    return;
                } else {
                    System.out.println("Requete nouvelleDemandes : session ok");
                }
                JSONObject sessionJson = (JSONObject) session.getAttribute(UserServlet.USER);
                Integer userId = sessionJson.getInt("userId");
                User user = UserManager.getUser(userId);

                //get the JSON information
                ArrayList<Loan> loans = new ArrayList<>();
                Tournament tournament = TournamentManager.getTournament(request.getInt("tId"));
                JSONArray cardsArray = request.getJSONArray("cards");
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

                resp.setContentType("application/json");
                PrintWriter out = resp.getWriter();

                if(failed.length() == 0){
                    LoanManager.insertLoans(loans);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    JSONObject arrayOk = new JSONObject();
                    arrayOk.put("nouvelleDemande","ok");
                    out.print(arrayOk);
                    out.flush();
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(failed);
                    out.flush();
                }
            }
            else {
                System.out.println("Loan Servlet POST : Erreur typeRequest");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.	SC_INTERNAL_SERVER_ERROR , e.getMessage());
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

        System.out.println("_________________________________");
        System.out.println("LoanServlet - "+request);
        System.out.println(req.getMethod());
        Enumeration<String> headerNames = req.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println("Header Name - " + headerName + ", Value - " + req.getHeader(headerName));
        }
        Enumeration<String> params = req.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = params.nextElement();
            System.out.println("Parameter Name - "+paramName+", Value - "+req.getParameter(paramName));
        }

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

                User borrower = UserManager.getUser(borrowerId);
                Tournament tourn = TournamentManager.getTournament(tournamentID);

               /* Email email = new SimpleEmail();
                email.setHostName("smtp.googlemail.com");
                email.setSmtpPort(465);
                email.setAuthenticator(new DefaultAuthenticator("notif.teamrenaissance@gmail.com", "b93PPUnFQmrd"));
                email.setSSLOnConnect(true);
                email.setFrom("notif.teamrenaissance@gmail.com");
                email.setSubject(lender.getUsername()+" vous prête de nouvelles cartes pour le "+tourn.getName());
                String msg = "Bonjour "+borrower.getUsername()+",";
                msg += System.lineSeparator();
                msg += System.lineSeparator();
                msg += lender.getUsername()+" peut vous prêter de nouvelles cartes pour le "+tourn.getName();
                msg += System.lineSeparator();
                msg+= "Rendez-vous sur https://www.teamrenaissance.fr/ dans l'onglet Mes Prêts après connexion pour en consulter l'état.";
                email.setMsg(msg);
                email.addTo(borrower.getEmail());
                email.send(); */


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
            /*
                User borrower = UserManager.getUser(borrowerId);
                User lender = UserManager.getUser(lenderId);
                Tournament tourn = TournamentManager.getTournament(tournamentID);
                Email email = new SimpleEmail();
                email.setHostName("smtp.googlemail.com");
                email.setSmtpPort(465);
                email.setAuthenticator(new DefaultAuthenticator("notif.teamrenaissance@gmail.com", "b93PPUnFQmrd"));
                email.setSSLOnConnect(true);
                email.setFrom("notif.teamrenaissance@gmail.com");
                email.setSubject(lender.getUsername()+"  pour le "+tourn.getName());
                String msg = "Bonjour "+borrower.getUsername()+",";
                msg += System.lineSeparator();
                msg += System.lineSeparator();
                msg += lender.getUsername()+" a modifié votre prêt pour le "+tourn.getName();
                msg += System.lineSeparator();
                msg+= "Rendez-vous sur https://www.teamrenaissance.fr/ dans l'onglet Mes Prêts après connexion pour consulter l'état de vos prêts.";
                email.setMsg(msg);
                email.addTo(borrower.getEmail());
                email.send();
            */
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
