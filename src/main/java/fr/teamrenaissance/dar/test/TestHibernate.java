package fr.teamrenaissance.dar.test;

import fr.teamrenaissance.dar.entities.User;
import fr.teamrenaissance.dar.managers.HibernateUtil;
import fr.teamrenaissance.dar.managers.UserManager;

public class TestHibernate {

    public static void main(String args[]) throws Exception {
        try {
            UserManager manager = new UserManager();

            manager.insertUser("Gamain", "Jeanne", "Naoko", "jeanne.gamain@gmail.com",
                    "hello", "azerty", null, null, "0777777777",
                    null, null);

            User user = null;
            user = manager.getUser(1);
            System.out.println(user.getEmail());

            user = manager.getUserByEmail("jeanne.gamain@gmail.com");
            System.out.println(user.getUserID());

        }finally{
            HibernateUtil.getSessionFactory().close();
        }
    }
}
