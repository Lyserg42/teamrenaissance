package fr.teamrenaissance.dar.servlets;

import fr.teamrenaissance.dar.entities.User;
import fr.teamrenaissance.dar.managers.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

@WebServlet(name = "UserServlet")
public class UserServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            JSONObject obj = new JSONObject();
            if(req.getParameter("typeRequest").equals("inscription")){
                String name = req.getParameter("name");
                String firstanme = req.getParameter("firstname");
                String username = req.getParameter("login");
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String address = req.getParameter("address");
                String avatar = req.getParameter("avatar");
                String phoneNumber = req.getParameter("phoneNumber");
                String dciNumber = req.getParameter("dciNumber");
                String fb = req.getParameter("facebook");
                String tw = req.getParameter("twitter");

                obj = UserManager.newUser(name,firstanme,email,username,password,
                        address,avatar,dciNumber,phoneNumber,fb,tw);
            }

            if(req.getParameter("typeRequest").equals("connection")){
                String login = req.getParameter("login");
                String password = req.getParameter("password");
                obj= UserManager.connectionUser(login,password);
            }
            if(req.getParameter("typeRequest").equals("getUser")){
                String login = req.getParameter("login");
                obj=  UserManager.getUser(login);

            }
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.print(obj);
            out.flush();

        } catch(Exception e){
            System.err.println("user not inserted");
        }

    }
}
