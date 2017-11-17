package fr.teamrenaissance.dar.servlets;

import fr.teamrenaissance.dar.managers.CardManager;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CardServlet")
public class CardServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONObject obj = new JSONObject();
        if(request.getParameter("typeRequest").equals("findOneCard")){
            String name = request.getParameter("name");
           obj =  CardManager.searchCardbyName(name);
        }
        if(request.getParameter("typeRequest").equals("findcards")){
            JSONObject js = ServletUtils.getJsonFromRequest(request);
            try {
                JSONArray listCards = js.getJSONArray("cards");
                obj = CardManager.searchCards(listCards);

            }catch (Exception e){

            }
        }
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(obj);
        out.flush();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
