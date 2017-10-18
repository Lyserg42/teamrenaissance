package fr.teamrenaissance.dar.test;

import fr.teamrenaissance.dar.managers.HibernateUtil;
import fr.teamrenaissance.dar.managers.UserManager;

public class TestHibernate {

    public static void main(String args[]) throws Exception {
        UserManager manager = new UserManager();
        manager.getUser(3);
        //manager.insertUser("truc", "muche", "chouette", "machin", "chose", "c'est", "long!");

        HibernateUtil.getSessionFactory().close();
    }
}
