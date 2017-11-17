package fr.teamrenaissance.dar.managers;

import fr.teamrenaissance.dar.entities.Card;
import fr.teamrenaissance.dar.entities.Loan;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CardManager {

    //Sarra


    /**
     * Return JSONObject contains 2 array: succes array for cards existing in DB and
     *failed array for cards not existing in BD
     *@param cardsList the array contains cards that user is looking for
     */
    public static JSONObject searchCards(JSONArray cardsList){
        JSONObject res = new JSONObject();
        JSONArray succes = new JSONArray();
        JSONArray failed = new JSONArray();
        for(int i =0; i < cardsList.length();i++){
            try {
                Card c = getCardByName(cardsList.getString(i));
                if( c == null){
                    failed.put(cardsList.getString(i));
                }
                else{
                   succes.put(cardsList.getString(i));
                }
            }catch (Exception e){

            }

        }
        try {
            res.put("succes", succes);
            res.put("failed",failed);
        }catch(Exception e){

        }
        return  res;
    }

    /**
     *use for search one card
     * @param name the name of the card
     * @return a JSONObject contains result of the research
     */
    public static JSONObject searchCardbyName(String name) {
       Card c = getCardByName(name);
       JSONObject obj = new JSONObject();
       if(c == null){
           try {
               obj.put("searcheCardFailed", name);
           }catch (Exception e){

           }
       }
       try {
           obj.put("name", c.getName());
           obj.put("picture",c.getPicture());
           obj.put("legacyLegal",c.getIsLegacyLegal());
           obj.put("modernLega",c.getIsModernLegal());
           obj.put("standard legal",c.getIsStandardLegal());

       }catch (Exception e){

       }
        return obj;

    }


    public static Card getCardByName(String name){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Card card = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Card> cq = builder.createQuery(Card.class);
            Root<Card> root = cq.from(Card.class);
            cq.select(root);
            cq.where( builder.equal(root.get("name"), name));
            List<Card> cardList = session.createQuery(cq).getResultList();
            /*
            for(Card c : cardList){
                if(c.getName().equals(name)){
                    card = c;
                }
            }*/
            if(!cardList.isEmpty()){
                card = cardList.get(0);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return card;
    }
 /**** method not use **/

    // ask several cards
    // to remove ?

    public JSONObject askSeveralCard(String cardsAsk){
        String[] cardsName = cardsAsk.split("\n");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        JSONObject obj = new JSONObject();
        CriteriaQuery cq = session.getCriteriaBuilder().createQuery(Card.class);
        cq.from(Card.class);
        Card ca = null;
        List<Card> cardList = session.createQuery(cq).getResultList();
        for(int i =0; i < cardsName.length;i++){
            for(Card c: cardList){
                if(c.getName().equals(cardsName[i])){
                  ca = c;
                  break;
                }
            }
            if(ca == null){
                try {
                    obj.put(cardsName[i],"not exist");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    obj.put(cardsName[i],ca);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        tx.commit();
        session.close();
        return  obj;

    }


    /* only use in this classe */
    /* not use here to remove ?*/
    private static Card getCard(int cardID){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Card card = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            card = session.get(Card.class, cardID);
            Hibernate.initialize(card);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return card;
    }

    public JSONObject searchCardbyId(int idCard) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        JSONObject obj = new JSONObject();
        CriteriaQuery cq = session.getCriteriaBuilder().createQuery(Card.class);
        cq.from(Card.class);
        Card ca = null;
        List<Card> cardList = session.createQuery(cq).getResultList();
        for (Card c : cardList) {
            if (c.getCardID() == idCard) {
                ca = c;
                break;
            }
        }
        if (ca == null) {
            try {
                obj.put(Integer.toString(idCard), "not exist");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                obj.put(Integer.toString(idCard), ca);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        tx.commit();
        session.close();
        return obj;

    }



    public static void insertCard(Card card){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(card);
            session.flush();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            //throw e;
        } finally {
            session.close();
        }
    }
}
