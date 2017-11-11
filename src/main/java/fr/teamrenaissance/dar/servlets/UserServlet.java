package fr.teamrenaissance.dar.servlets;

import fr.teamrenaissance.dar.entities.User;
import fr.teamrenaissance.dar.managers.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
            HttpSession userSession = req.getSession();
            if(req.getParameter("typeRequest").equals("inscription")){
              /*  JSONObject newUser = ServletUtils.getJsonFromRequest(req);
                String name = newUser.getString("name");
                String firstanme = newUser.getString("firstname");
                String username = newUser.getString("login");
                String email = newUser.getString("email");
                String password = newUser.getString("password");
                String address = newUser.getString("address");
                String avatar = newUser.getString("avatar");
                String phoneNumber = newUser.getString("phoneNumber");
                String dciNumber = newUser.getString("dciNumber");
                String fb = newUser.getString("facebook");
                String tw = newUser.getString("twitter");*/
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
               if(obj.get("userSuccesConnection") !=null){
                    userSession = req.getSession();
                    userSession.setAttribute("user", obj.get("userSuccesConnection"));
                }

            }
            if(req.getParameter("typeRequest").equals("getUser")){
                String login = req.getParameter("login");
                obj=  UserManager.getUser(login);

            }
            if(req.getParameter("typeRequest").equals("setUserProfil")){
               /* JSONObject newUserProfil = ServletUtils.getJsonFromRequest(req);
                String name = newUserProfil.getString("name");
                String firstanme = newUserProfil.getString("firstname");
                //String username = newUserProfil.getString("login");
                String username =
                        ((JSONObject) userSession.getAttribute("user")).getString("login");

                String email = newUserProfil.getString("email");
                String password = newUserProfil.getString("password");
                String address = newUserProfil.getString("address");
                String avatar = newUserProfil.getString("avatar");
                String phoneNumber = newUserProfil.getString("phoneNumber");
                String dciNumber = newUserProfil.getString("dciNumber");
                String fb = newUserProfil.getString("facebook");
                String tw = newUserProfil.getString("twitter");*/
                String username =
                        ((JSONObject) userSession.getAttribute("user")).getString("login");
                //String username = req.getParameter("login");
                String name = req.getParameter("name");
                String firstname = req.getParameter("firstname");
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String address = req.getParameter("address");
                String avatar = req.getParameter("avatar");
                String phoneNumber = req.getParameter("phoneNumber");
                String dciNumber = req.getParameter("dciNumber");
                String fb = req.getParameter("facebook");
                String tw = req.getParameter("twitter");
                obj= UserManager.setUserProfil(name,firstname,email,username,
                        password,address,avatar,dciNumber,phoneNumber,
                        fb,tw);


            }
            if(req.getParameter("typeRequest").equals("deconnection")){
                userSession.setAttribute("user",null);
                obj.put("deconection","succes");
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
