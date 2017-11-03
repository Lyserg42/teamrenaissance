package fr.teamrenaissance.dar.servlets;

import fr.teamrenaissance.dar.entities.Tournament;
import fr.teamrenaissance.dar.managers.LoanManager;
import fr.teamrenaissance.dar.managers.TournamentManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class LoanServlet  extends HttpServlet {

    @Override
   public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //gets the loans of a user per tournaments
        //gets the userID
        Integer userId;
        try {
            userId = Integer.valueOf(req.getParameter("userId"));
        } catch (NullPointerException | NumberFormatException e){
            return;
        }
        //gets tournaments in chronological order
        TournamentManager tournamentManager = new TournamentManager();
        List<Tournament> tournaments = tournamentManager.getAllTournaments();

        LoanManager loanManager = new LoanManager();

        //create the JSON
        try {
            JSONObject resultJson = new JSONObject();
            JSONArray tournamentsArray = new JSONArray();

            for (Tournament tournament : tournaments) {

                JSONArray lentCards = loanManager.getLentCardsJson(userId, tournament.getTournamentID());
                JSONArray borrowedCards = loanManager.getBorrowedCardsJson(userId, tournament.getTournamentID());
                JSONArray demands = loanManager.getDemandsJson(userId, tournament.getTournamentID());

                if(lentCards.length() != 0 || borrowedCards.length() != 0 || demands.length() != 0){
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
        }
    }
}
