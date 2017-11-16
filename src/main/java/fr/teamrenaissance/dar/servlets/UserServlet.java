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
    private static final String USER = "user";
    private static final String SUCCESCONNECTION ="userSuccesConnection";
    private static final String DEFAULT_AVATAR = "";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            JSONObject obj = new JSONObject();
            HttpSession userSession ;
            JSONObject request =  ServletUtils.getJsonFromRequest(req);
            String typeRequeste = request.getString("typeRequest");
            if(typeRequeste.equals("inscription")){

                String name = request.getString("name");
                String firstanme = request.getString("firstname");
                String username = request.getString("login");
                String email = request.getString("email");
                String password = request.getString("password");
                //String address = request.getString("address");
                //String avatar = request.getString("avatar");
                //String phoneNumber = request.getString("phoneNumber");
                //String dciNumber = request.getString("dciNumber");
                //String fb = request.getString("facebook");
                //String tw = request.getString("twitter");
                /*String name = req.getParameter("name");
                String firstanme = req.getParameter("firstname");
                String username = req.getParameter("login");
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String address = req.getParameter("address");
                String avatar = req.getParameter("avatar");
                String phoneNumber = req.getParameter("phoneNumber");
                String dciNumber = req.getParameter("dciNumber");
                String fb = req.getParameter("facebook");
                String tw = req.getParameter("twitter");*/

                obj = UserManager.newUser(name,firstanme,email,username,password,
                        "",DEFAULT_AVATAR,"","","","");
            }

            if(typeRequeste.equals("connexion")){
                String login = request.getString("login");
                String password = request.getString("pasword");
               /* String login = req.getParameter("login");
                String password = req.getParameter("password");*/
                userSession = req.getSession();
               if(!userSession.isNew()){
                   obj.put("userFailedConnection","you must to disconnect");

               }else {

                   obj = UserManager.connectionUser(login, password);
                   if (obj.has(SUCCESCONNECTION)) {

                       userSession.setAttribute(USER, obj.get("userSuccesConnection"));
                   } else {
                       userSession.invalidate();
                   }
               }


            }
            if(typeRequeste.equals("getUser")){
                String login = request.getString("uName");
                obj=  UserManager.getUser(login);

            }
            if(typeRequeste.equals("setUserProfil")){

                //String username = newUserProfil.getString("login");
               // String username =
                //        ((JSONObject) userSession.getAttribute("user")).getString("login");

                userSession = req.getSession();
                String username =
                        ((JSONObject) userSession.getAttribute(USER)).getString("login");
                if(username== null){
                    obj.put("setProfilFailed","you must to be connect");
                } else{
                    String name = request.getString("name");
                    String firstname = request.getString("firstname");
                    String email = request.getString("email");
                    String password = request.getString("password");
                    String address = request.getString("address");
                    String avatar = request.getString("avatar");
                    String phoneNumber = request.getString("phoneNumber");
                    String dciNumber = request.getString("dciNumber");
                    String fb = request.getString("facebook");
                    String tw = request.getString("twitter");

                    //String username = req.getParameter("login");
                   /* String name = req.getParameter("name");
                    String firstname = req.getParameter("firstname");
                    String email = req.getParameter("email");
                    String password = req.getParameter("password");
                    String address = req.getParameter("address");
                    String avatar = req.getParameter("avatar");
                    String phoneNumber = req.getParameter("phoneNumber");
                    String dciNumber = req.getParameter("dciNumber");
                    String fb = req.getParameter("facebook");
                    String tw = req.getParameter("twitter");*/
                    obj= UserManager.setUserProfil(name,firstname,email,username,
                            password,address,avatar,dciNumber,phoneNumber,
                            fb,tw);
                }


            }
            if(typeRequeste.equals("deconnection")){
                userSession = req.getSession();
                userSession.invalidate();
                obj.put("deconnection","succes");
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
