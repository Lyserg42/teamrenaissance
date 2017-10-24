package fr.teamrenaissance.dar.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Loan")
public class Loan {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private int loanID;
    private int tournamentID;
    private int cardID;
    private int borrowerID;
    private int lenderID;
    private boolean returned;
    private boolean done;

    public Loan(int tournamentID, int cardID, int borrowerID, int lenderID, boolean returned, boolean done) {
        this.tournamentID = tournamentID;
        this.cardID = cardID;
        this.borrowerID = borrowerID;
        this.lenderID = lenderID;
        this.returned = returned;
        this.done = done;
    }

    public Loan(){}

    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public int getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public int getBorrowerID() {
        return borrowerID;
    }

    public void setBorrowerID(int borrowerID) {
        this.borrowerID = borrowerID;
    }

    public int getLenderID() {
        return lenderID;
    }

    public void setLenderID(int lenderID) {
        this.lenderID = lenderID;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
