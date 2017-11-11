package fr.teamrenaissance.dar.servlets;

import fr.teamrenaissance.dar.entities.Tournament;
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

public class TournamentServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //get all the tournaments in chronological order
        List<Tournament> tournamentList = TournamentManager.getAllTournaments();
        JSONObject tournaments = new JSONObject();
        JSONArray tournamentArray = new JSONArray();
        try {
            for (Tournament t : tournamentList) {
                tournamentArray.put(t.getJSONObject());
            }
            tournaments.put("tournaments", tournamentArray);

            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.print(tournaments);
            writer.flush();
            writer.close();

        }catch(JSONException e){
            e.printStackTrace();
            resp.sendError(HttpServletResponse.	SC_INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }
}
