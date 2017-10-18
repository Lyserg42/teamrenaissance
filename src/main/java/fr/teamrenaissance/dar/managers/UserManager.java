package fr.teamrenaissance.dar.managers;

import fr.teamrenaissance.dar.db.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserManager {

    public User insertUser(String name, String firstname, String username, String email, String password,
                           String address, String avatar) throws Exception {

        Session session = HibernateUtil.getSessionFactory().openSession();
        User user = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            user = new User(name, firstname, username, email, password, address, avatar);
            session.save(user);
            session.flush();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return user;
    }

    public User getUser(int userID){
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user = null;
        try {
            user = (User) session.load(User.class, new Integer(userID));
            System.out.println("mail = " + user.getEmail());
        } finally {
            session.close();
        }
        return user;
    }

}
