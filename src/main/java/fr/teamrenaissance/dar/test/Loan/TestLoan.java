package fr.teamrenaissance.dar.test.Loan;

import fr.teamrenaissance.dar.managers.HibernateUtil;
import fr.teamrenaissance.dar.managers.LoanManager;
import fr.teamrenaissance.dar.managers.TournamentManager;


public class TestLoan {
    public static void main(String[] args)throws Exception{
        try{
            //System.out.println(TournamentManager.getAllTournaments());
            //LoanManager.getLentCardsJson(1, 2);
            LoanManager.deleteLoans(2, null, 2, 1, 0);
        }finally{
            HibernateUtil.shutdown();
        }
    }
}
