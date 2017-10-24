package fr.teamrenaissance.dar.managers;

import fr.teamrenaissance.dar.entities.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserManager {

    public void insertUser(User user) throws Exception {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(user);
            session.flush();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public User getUser(int userID){
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            user = session.get(User.class, userID);
            Hibernate.initialize(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return user;
    }

    public User getUserByEmail(String email){
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from User where email = :mail");
            query.setString("mail", email);
            List result = query.list();
            if(!result.isEmpty()) {
                user = (User) result.get(0);
            }
            Hibernate.initialize(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return user;
    }
}
