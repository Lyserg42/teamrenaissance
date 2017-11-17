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
      // doPost(req,resp);
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            resp.setContentType("text/plain");
            PrintWriter out = resp.getWriter();

           JSONObject obj = new JSONObject();
            HttpSession userSession ;
            JSONObject request =  ServletUtils.getJsonFromRequest(req);
            String typeRequest = request.getString("typeRequest");

            if(typeRequest.equals("inscription")){

                String name = request.getString("name");
                String firstanme = request.getString("firstname");
                String username = request.getString("login");
                String email = request.getString("email");
                String password = request.getString("password");
                String address = request.getString("address");
                String avatar = request.getString("avatar");
                String phoneNumber = request.getString("phoneNumber");
                String dciNumber = request.getString("dciNumber");
                String fb = request.getString("facebook");
                String tw = request.getString("twitter");

               obj = UserManager.newUser(name,firstanme,email,
               username,password, "",DEFAULT_AVATAR,
                       "","",
                       "","","","");
               if(!obj.get("newuser").equals("succes")){
                   resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                   out.print(obj);

               }else{
                   resp.setStatus(HttpServletResponse.SC_OK);

               }
            }

            else if(typeRequest.equals("connexion")){

                String login = request.getString("login");
                String password = request.getString("password");
                userSession = req.getSession();
               if(!userSession.isNew()){
                   obj.put("userFailedConnection","you must disconnect");
                   resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
               }else {

                   obj = UserManager.connectionUser(login, password);
                   if (obj.has(SUCCESCONNECTION)) {

                       userSession.setAttribute(USER, obj.get("userSuccesConnection"));
                       resp.setStatus(HttpServletResponse.SC_OK);

                   } else {
                       userSession.invalidate();
                       resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                   }
               }


            }
           /* else if(typeRequest.equals("getUser")){
                String login = request.getString("uName");
                obj=  UserManager.getUser(login);

            }
            else if(typeRequest.equals("setUserProfil")){

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
                    String city = request.getString("city");
                    String zipCode = request.getString("zipCode");
                    obj= UserManager.setUserProfil(name,firstname,email,username,
                            password,address,avatar,dciNumber,phoneNumber,
                            fb,tw,city,zipCode);
                }


            }
            else if(typeRequest.equals("deconnection")){
                userSession = req.getSession();
                userSession.invalidate();
                obj.put("deconnection","succes");
            }
            else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }*/



            out.flush();
            out.close();

        } catch(Exception e){
            System.err.println("user not inserted");
        }

    }
}
