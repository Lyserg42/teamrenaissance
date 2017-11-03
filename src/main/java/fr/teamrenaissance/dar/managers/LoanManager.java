package fr.teamrenaissance.dar.managers;

import fr.teamrenaissance.dar.entities.Card;
import fr.teamrenaissance.dar.entities.Loan;
import fr.teamrenaissance.dar.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

public class LoanManager {
/*
    public void createAndInsertLoan(JSONObject loan){
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
*/


    /**
     * Returns the JSON array containing the cards lent by a user for a given tournament.
     * @param userID the ID of the user who lends the cards
     * @param tournamentID the ID of the tournament where the cards are lent
     * @throws JSONException
     */
    public JSONArray getLentCardsJson(Integer userID, Integer tournamentID) throws JSONException {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // select u.username, l.cardID, count(*) from Loan l, User u
            // where u.userID = l.borrowerID and l.lenderID = userID and l.tournamentID = tournamentID
            // group by l.borrowerID, l.cardID;

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery  = builder.createQuery(Object[].class);
            //MULTISELECT
            Root<Loan> loanRoot = criteriaQuery.from(Loan.class);
            criteriaQuery.multiselect(loanRoot.get("borrower"), loanRoot.get("card"), builder.countDistinct(loanRoot));
            criteriaQuery.where(builder.equal(loanRoot.get("lender"), userID), builder.equal(loanRoot.get("tournament"), tournamentID));
            //GROUP BY
            criteriaQuery.groupBy(loanRoot.get("borrower"), loanRoot.get("card"));

            Query<Object[]> query = session.createQuery(criteriaQuery);
            List<Object[]> result = query.getResultList();

            tx.commit();

            return this.transformToJsonArray(result);

        } catch (JSONException e){
            throw e;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    /**
     * Returns the JSON array containing the cards borrowed by a user for a given tournament.
     * @param userID the ID of the user who borrows the cards
     * @param tournamentID the ID of the tournament where the cards are lent
     * @throws JSONException
     */
    public JSONArray getBorrowedCardsJson(Integer userID, Integer tournamentID) throws JSONException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery  = builder.createQuery(Object[].class);
            //MULTISELECT
            Root<Loan> loanRoot = criteriaQuery.from(Loan.class);
            criteriaQuery.multiselect(loanRoot.get("lender"), loanRoot.get("card"), builder.countDistinct(loanRoot));
            criteriaQuery.where(
                    builder.equal(loanRoot.get("borrower"), userID),
                    builder.equal(loanRoot.get("tournament"), tournamentID),
                    builder.isNotNull(loanRoot.get("lender")));
            //GROUP BY
            criteriaQuery.groupBy(loanRoot.get("lender"), loanRoot.get("card"));

            Query<Object[]> query = session.createQuery(criteriaQuery);
            List<Object[]> result = query.getResultList();

            tx.commit();

            return this.transformToJsonArray(result);

        } catch (JSONException e){
            throw e;
        }catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    private JSONArray transformToJsonArray(List<Object[]> queryResult) throws JSONException{
        //make a map from the result
        Map<User, List<Object[]>> resultMap =
                queryResult.stream().collect(
                        Collectors.groupingBy(line -> (User)Arrays.asList(line).get(0))
                );

        //Create the JSON array
        JSONArray jsonArray = new JSONArray();
        for (User user : resultMap.keySet()) {
            JSONObject loan = new JSONObject();
            loan.put("uName", user.getUsername());
            loan.put("uID", user.getUserID());

            //create the JSON array of cards
            JSONArray cards = new JSONArray();
            for(Object[] line : resultMap.get(user)){
                //create a card json object
                JSONObject cardJson = new JSONObject();
                cardJson.put("qty", line[2]);
                Card card = (Card)line[1];
                cardJson.put("cName", card.getName());
                cardJson.put("cID", card.getCardID());
                //add the json card to the cards array
                cards.put(cardJson);
            }
            loan.put("cards", cards);
            jsonArray.put(loan);
        }

        return jsonArray;
    }


    /**
     * Returns the JSON array containing the cards demanded by a user for a given tournament and which have not yet find a lender.
     * @param userID the ID of the user who borrows the cards
     * @param tournamentID the ID of the tournament where the cards are lent
     * @throws JSONException
     */
    public JSONArray getDemandsJson(Integer userID, Integer tournamentID) throws JSONException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery  = builder.createQuery(Object[].class);
            //MULTISELECT
            Root<Loan> loanRoot = criteriaQuery.from(Loan.class);
            criteriaQuery.multiselect(loanRoot.get("card"), builder.countDistinct(loanRoot));
            criteriaQuery.where(
                    builder.equal(loanRoot.get("borrower"), userID),
                    builder.equal(loanRoot.get("tournament"), tournamentID),
                    builder.isNull(loanRoot.get("lender")));
            //GROUP BY
            criteriaQuery.groupBy(loanRoot.get("card"));

            Query<Object[]> query = session.createQuery(criteriaQuery);
            List<Object[]> result = query.getResultList();

            tx.commit();

            //Create the JSON array
            JSONArray demands = new JSONArray();
            for (Object[] line : result) {
                //create a card json object
                JSONObject cardJson = new JSONObject();
                cardJson.put("qty", line[1]);
                Card card = (Card)line[0];
                cardJson.put("cName", card.getName());
                cardJson.put("cID", card.getCardID());
                //add the json card to the demands array
                demands.put(cardJson);
            }

            return demands;

        } catch (JSONException e){
            throw e;
        }catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

}
