package fr.teamrenaissance.dar.test.Loan;

import fr.teamrenaissance.dar.managers.HibernateUtil;
import fr.teamrenaissance.dar.managers.LoanManager;
import fr.teamrenaissance.dar.managers.TournamentManager;


public class TestLoan {
    public static void main(String[] args)throws Exception{
        try{
            LoanManager manager = new LoanManager();
            TournamentManager tManager = new TournamentManager();
            System.out.println(tManager.getAllTournaments());
            manager.getLendedCardsJson(1, 2);
        }finally{
            HibernateUtil.shutdown();
        }
    }
}
