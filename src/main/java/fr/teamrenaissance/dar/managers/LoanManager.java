package fr.teamrenaissance.dar.managers;

import fr.teamrenaissance.dar.entities.Card;
import fr.teamrenaissance.dar.entities.Loan;
import fr.teamrenaissance.dar.entities.Tournament;
import fr.teamrenaissance.dar.entities.User;
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
     * Returns the JSON object containing the cards lent by a user for a given tournament.
     * @param userID the ID of the user that lend the cards
     * @param tournamentID the ID of the tournament where the cards are lent
     */
    public JSONArray getLendedCardsJson(Integer userID, Integer tournamentID) throws JSONException {
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

            for(Object[] line : result){
                System.out.println(((User)line[0]).getUsername()+" "+((Card)line[1]).getName()+" "+line[2]);
            }

            //make a map from the result
            Map<String, List<Object[]>> resultMap =
                    result.stream().collect(
                    Collectors.groupingBy(line -> ((User)Arrays.asList(line).get(0)).getUsername())
            );

            //Create the JSON array
            JSONArray lentCards = new JSONArray();
            for (String username : resultMap.keySet()) {
                JSONObject loan = new JSONObject();
                loan.put("uName", username);

                //create the JSON array of cards
                JSONArray cards = new JSONArray();
                for(Object[] line : resultMap.get(username)){
                    //create a card json object
                    JSONObject cardJson = new JSONObject();
                    cardJson.put("qty", line[2]);
                    Card card = (Card)line[1];
                    cardJson.put("cName", card.getName());
                    //add the json card to the cards array
                    cards.put(cardJson);
                }
                loan.put("cards", cards);
                lentCards.put(loan);
            }
/*
            result.put("tName", tournament.getName());
            result.put("date", tournament.getDate().toString());
            result.put("lentCards", lentCards);
            */

            System.out.println(lentCards.toString());
            return lentCards;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

}
