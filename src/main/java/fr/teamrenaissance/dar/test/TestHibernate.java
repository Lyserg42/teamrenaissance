package fr.teamrenaissance.dar.test;

import fr.teamrenaissance.dar.entities.User;
import fr.teamrenaissance.dar.managers.HibernateUtil;
import fr.teamrenaissance.dar.managers.UserManager;

public class TestHibernate {

    public static void main(String args[]) throws Exception {
        try {
            UserManager manager = new UserManager();
            User user = null;
            user = manager.getUser(2);
            System.out.println(user.getEmail());
            //manager.insertUser("truc", "muche", "chouette", "machin4", "chose", "c'est", "long!");
            user = manager.getUserByEmail("machin4");
            System.out.println(user.getUserID());

        }finally{
            HibernateUtil.getSessionFactory().close();
        }
    }
}
