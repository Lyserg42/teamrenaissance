package fr.teamrenaissance.dar.managers;

import fr.teamrenaissance.dar.entities.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONObject;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserManager {

    ////sarra////
    public JSONObject newUser(String name, String firstname, String mail, String username,
                          String password, String adress, String avatar, String dciNumber,
                           String phoneNumber, String fb, String tw) throws Exception{
        JSONObject obj = new JSONObject();
        if(!isValideLogin(username)){
            obj.put("newuser","failed");
        }
        else {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            User u = new User();
            u.setAddress(adress);
            u.setAvatar(avatar);
            u.setName(name);
            u.setFirstname(firstname);
            u.setEmail(mail);
            u.setUsername(username);
            u.setPassword(password);
            u.setDciNumber(dciNumber);
            u.setPhoneNumber(phoneNumber);
            u.setFacebook(fb);
            u.setTwitter(tw);
            session.save(u);
            session.flush();
            tx.commit();
            session.close();
            obj.put("newuser","succes");
        }
        return obj;

    }


/*
 check if login is not use
 */
    private boolean isValideLogin(String login){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        CriteriaQuery<User> cq = session.getCriteriaBuilder().createQuery(User.class);
        cq.from(User.class);
        boolean result = true;
        List<User> users = session.createQuery(cq).getResultList();
        for(User u:users) {
            if(u.getUsername().equals(login)) {
                result =false;
            }
        }
        tx.commit();
        session.close();
        return result;

    }
/////////////////////////////////jeanne ///////////////

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
