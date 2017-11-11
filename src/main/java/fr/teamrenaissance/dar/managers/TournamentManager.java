package fr.teamrenaissance.dar.managers;

import fr.teamrenaissance.dar.entities.Tournament;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TournamentManager {

    public static Tournament getTournament(int tournamentID){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Tournament tournament = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            tournament = session.get(Tournament.class, tournamentID);
            Hibernate.initialize(tournament);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return tournament;
    }

    /**
     * Returns all the Tournaments in chronological order, from the newest to the oldest.
     */
    public static  List<Tournament> getAllTournaments(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Tournament> criteriaQuery  = builder.createQuery(Tournament.class);
            //SELECT
            Root<Tournament> root = criteriaQuery.from(Tournament.class);
            criteriaQuery.select(root);
            //ORDER BY
            criteriaQuery.orderBy(builder.desc(root.get("date")));

            Query<Tournament> query = session.createQuery(criteriaQuery);
            List<Tournament> result = query.getResultList();

            tx.commit();
            return result;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
