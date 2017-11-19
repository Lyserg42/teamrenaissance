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
    public static final String USER = "user";
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

           JSONObject obj = new JSONObject();
            HttpSession userSession ;
            JSONObject request =  ServletUtils.getJsonFromRequest(req);
            String typeRequest = request.getString("typeRequest");
            PrintWriter out = resp.getWriter();
            if(typeRequest.equals("inscription")){
                String name = request.getString("name");
                String firstname = request.getString("firstname");
                String username = request.getString("login");
                String email = request.getString("email");
                String password = request.getString("password");

               obj = UserManager.newUser(name,firstname,email,
               username,password, "",DEFAULT_AVATAR,
                       "","",
                       "","","","");


               if(!obj.get("newuser").equals("succes")){
                   resp.setContentType("application/json");

                   resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                   out.print(obj);

               }else{
                   resp.setContentType("text/plain");

                   resp.setStatus(HttpServletResponse.SC_OK);

               }
            }

            else if(typeRequest.equals("connexion")){
                resp.setContentType("text/plain");

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
            else if(typeRequest.equals("getUser")){
                String uName = request.getString("uName");
                if(uName.equals("")){
                  userSession=  req.getSession(false);
                  if(userSession== null){
                      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                  }
                  else{
                      uName = ((JSONObject)
                              userSession.getAttribute(USER)).getString("login");
                  }
                }
                obj=  UserManager.getUser(uName);
                if(obj.has("getUserFailed")){
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
                else{
                    resp.setContentType("application/json");
                    resp.setStatus(HttpServletResponse.SC_OK);
                    out.print(obj.get("getUserSucces"));

                }

            }
            else if(typeRequest.equals("setUserProfil")){

                userSession = req.getSession(false);
                if(userSession==null) {
                    resp.setContentType("application/json");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    obj.put("setUserProfil","Unknown error");
                    out.print(obj);

                }else{
                    String username =
                            ((JSONObject) userSession.getAttribute(USER)).getString("login");
                    String name = request.getString("name");
                    String firstname = request.getString("firstName");
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
                    if(UserManager.isEqualsPassword(username,password)) {
                        if(request.has("newPassword")){
                            password = request.getString("newPassword");
                        }
                        obj = UserManager.setUserProfil(name, firstname, email, username,
                                password, address, avatar, dciNumber, phoneNumber,
                                fb, tw, city, zipCode);
                        if(obj.has("setProfilFailed")){
                            resp.setContentType("application/json");
                            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.print(obj);

                        }else{
                            resp.setStatus(HttpServletResponse.SC_OK);

                        }

                    }else{
                        resp.setContentType("application/json");
                        obj.put("setUserProfil","Password error");
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print(obj);


                    }
                }


            }
            else if(typeRequest.equals("deconnexion")){
                userSession = req.getSession();
                userSession.invalidate();
                obj.put("deconnexion","succes");
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }



            out.flush();
            out.close();

        } catch(Exception e){
            System.err.println("user not inserted");
        }

    }
}
