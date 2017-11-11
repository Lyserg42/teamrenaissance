package fr.teamrenaissance.dar.managers;

import fr.teamrenaissance.dar.entities.Card;
import fr.teamrenaissance.dar.entities.Loan;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CardManager {

    //Sarra

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

    public JSONObject searchCardbyName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        JSONObject obj = new JSONObject();
        CriteriaQuery cq = session.getCriteriaBuilder().createQuery(Card.class);
        cq.from(Card.class);
        Card ca = null;
        List<Card> cardList = session.createQuery(cq).getResultList();
        for (Card c : cardList) {
            if (c.getName().equals(name)) {
                ca = c;
                break;
            }
        }
        if (ca == null) {
            try {
                obj.put(name, "not exist");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                obj.put(name, ca);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        tx.commit();
        session.close();
        return obj;

    }




    // ask several cards

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


    public static Card getCard(int cardID){
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

    public static Card getCardByName(String name){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Card card = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            CriteriaQuery cq = session.getCriteriaBuilder().createQuery(Card.class);
            cq.from(Card.class);
            List<Card> cardList = session.createQuery(cq).getResultList();
            for(Card c : cardList){
                if(c.getName().equals(name)){
                    card = c;
                }
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
