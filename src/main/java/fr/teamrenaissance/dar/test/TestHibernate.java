package fr.teamrenaissance.dar.test;

import fr.teamrenaissance.dar.entities.User;
import fr.teamrenaissance.dar.managers.HibernateUtil;
import fr.teamrenaissance.dar.managers.UserManager;

public class TestHibernate {

    public static void main(String args[]) throws Exception {
        try {
            UserManager manager = new UserManager();
/*
            manager.insertUser(new User("Gamain", "Jeanne", "Naoko", "jeanne.gamain@gmail.com",
                    "helloooo", "xxx", null, null, "+33612345678",
                    null, null));
*/
            manager.insertUser(new User("Gamain", "Jeanne", "test", "test@",
                    "helloooo", "xxx", null, null, "0777777777",
                    null, null, null,"",""));

            User user = null;
            user = manager.getUser(1);
            if (user != null) System.out.println(user.getEmail());
            else System.out.println("NO USER FOUND");

            user = manager.getUserByEmail("jeanne.gamain@gmail.com");
            if (user != null) System.out.println(user.getUserID());
            else System.out.println("NO USER FOUND");

        }finally{
            HibernateUtil.shutdown();
        }
    }
}
