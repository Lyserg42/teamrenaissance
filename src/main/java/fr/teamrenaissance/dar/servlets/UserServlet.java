package fr.teamrenaissance.dar.servlets;

import fr.teamrenaissance.dar.entities.User;
import fr.teamrenaissance.dar.managers.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserServlet")
public class UserServlet extends HttpServlet {

    private UserManager userManager;

    public void init() throws ServletException{
        this.userManager = new UserManager();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            User user = new User();
            user.setName(req.getParameter("name"));
            user.setFirstname(req.getParameter("firstname"));
            user.setUsername(req.getParameter("username"));
            user.setEmail(req.getParameter("email"));
            user.setPassword(req.getParameter("password"));
            user.setAddress(req.getParameter("address"));
            user.setAvatar(req.getParameter("avatar"));
            user.setPhoneNumber(req.getParameter("phoneNumber"));
            user.setDciNumber(req.getParameter("dciNumber"));
            user.setFacebook(req.getParameter("facebook"));
            user.setTwitter(req.getParameter("twitter"));

            userManager.insertUser(user);

        } catch(Exception e){
            System.err.println("user not inserted");
        }

    }
}
