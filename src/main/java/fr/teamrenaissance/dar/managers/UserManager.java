package fr.teamrenaissance.dar.managers;

import fr.teamrenaissance.dar.entities.Card;
import fr.teamrenaissance.dar.entities.Loan;
import fr.teamrenaissance.dar.entities.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserManager {

    ////sarra////
    public static JSONObject newUser(String name, String firstname, String mail, String username,
                          String password, String adress, String avatar, String dciNumber,
                           String phoneNumber, String fb, String tw) throws Exception{
        JSONObject obj = new JSONObject();
        if(!isValideLogin(username)){
            obj.put("newuser","login already exist");
        }else {
            if (!passwordSolid(password)) {
                obj.put("newuser", "password not respect");
            } else {
                if (!isEnteredField(name, firstname, username, password, phoneNumber)) {
                    obj.put("newuser", "mandatory fields are not entered");
                } else {
                    if (!isValidPhone(phoneNumber)) {
                        obj.put("newuser", "phone number not valid");
                    } else {
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
                        obj.put("newuser", "succes");
                    }
                }
            }
        }
        return obj;

    }

/*
return JsonObject contains userId and list of his cards ask and not yet received
to remove ???
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
    return a jsonObject contains User's data
    uses the getUserByLogin function
     */
    public static JSONObject connectionUser(String login, String pass){
        JSONObject obj = new JSONObject();
        User u = getUserByLogin(login);
        JSONObject obju = new JSONObject();
        if(u == null){
            try {
                obju.put("userFailedConnection","user not registered");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            if (!u.getPassword().equals(pass)) {
                try {
                    obju.put("userFailedConnection", "invalid password");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                try {
                    obj.put("name", u.getName());
                    obj.put("firstname", u.getFirstname());
                    obj.put("email", u.getEmail());
                    obj.put("address", u.getAddress());
                    obj.put("avatar", u.getAvatar());
                    obj.put("phone number", u.getPhoneNumber());
                    obj.put("facebook", u.getFacebook());
                    obj.put("twitter", u.getTwitter());
                    obj.put("login", u.getUsername());
                    obju.put("userSuccesConnection", obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return obju;
    }
    /* return user's data from login*/

    public static JSONObject getUser(String login){
        JSONObject obj = new JSONObject();
        User u = getUserByLogin(login);
        JSONObject obju = new JSONObject();
        if(u == null){
            try {
                obju.put("getUserFailed","user not registered");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {

            try {
                obj.put("name", u.getName());
                obj.put("firstname", u.getFirstname());
                obj.put("email", u.getEmail());
                obj.put("address", u.getAddress());
                obj.put("avatar", u.getAvatar());
                obj.put("phone number", u.getPhoneNumber());
                obj.put("facebook", u.getFacebook());
                obj.put("twitter", u.getTwitter());
                obj.put("login", u.getUsername());
                obju.put("getUserSucces", obj);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return obju;

    }

    /* test if the email respect email format */
    private static boolean isValideEmail(String email) {
        boolean res = true;
        try {
            InternetAddress add = new InternetAddress(email);
            add.validate();
        } catch (AddressException e) {
            res = false;
        }
        return res;
    }


/* check  phoneNumber */
    private static boolean isValidPhone(String phoneNumber){
        boolean res = true;
        try {
            Integer.parseInt(phoneNumber);
        }catch (NumberFormatException e){
            res = false;
        }
        return  res;
    }

/*
 check if login is not use
 */
    private static boolean isValideLogin(String login){
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
/* test if a string contains a number
* */
    private static boolean containNumber(String s){
        char[] c = s.toCharArray();
        for(char i:c){
            if(Character.isDigit(i)){
                return true;
            }
        }
        return false;

    }

    /*test if a string contains a uppercase char*/

    private static boolean containUppercase(String s){
        char[] c = s.toCharArray();
        for(char i:c){
            if(Character.isUpperCase(i)){
                return true;
            }
        }
        return false;
    }

    /* test if password is solid*/
    private static boolean passwordSolid(String pssw){

        return ((pssw.length()>=8) && containNumber(pssw) && containUppercase(pssw));
    }

    /*test if mandatory fields are entered*/
    private static boolean isEnteredField(String name,
                                          String lastName,String userName,
                                          String password, String phoneNumber){
    if(name.equals("") ||  lastName.equals("") || userName.equals("")
            || password.equals("") || phoneNumber.equals("")){
            return false;
        }
        return true;
    }

    /*getUser by login*/

    private static User getUserByLogin(String loginUser){
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            CriteriaQuery<User> cq = session.getCriteriaBuilder().createQuery(User.class);
            cq.from(User.class);
            List<User> users = session.createQuery(cq).getResultList();
          for(User u: users){
              if(u.getUsername().equals(loginUser)){
                  user = u;
              }
          }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return user;
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
/* getUser by ID*/
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
