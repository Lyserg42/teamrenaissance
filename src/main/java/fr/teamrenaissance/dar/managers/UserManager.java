package fr.teamrenaissance.dar.managers;

import fr.teamrenaissance.dar.entities.Card;
import fr.teamrenaissance.dar.entities.Loan;
import fr.teamrenaissance.dar.entities.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.Collection;
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
return JsonObject contains userId and list of his cards ask and not yet received
 */
    public JSONObject askCards(int userId){
        SessionFactory sessFact = HibernateUtil.getSessionFactory();
        Session sess = sessFact.openSession();
        Transaction tr = sess.beginTransaction();
        CriteriaQuery cq = sess.getCriteriaBuilder().createQuery(Loan.class);
        cq.from(Loan.class);
        JSONObject obj = new JSONObject();
        Collection<Card> cards = new ArrayList<>();
        try {
            obj.put("userId",userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<Loan> loanList = sess.createQuery(cq).getResultList();
        for(Loan l: loanList){
            if(l.getBorrower().getUserID() == userId && l.getLender()== null){
                cards.add(l.getCard());
            }
        }
        try {
            obj.put("ask Cards",cards);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sess.flush();
        tr.commit();
        sess.close();
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
