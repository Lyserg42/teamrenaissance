package fr.teamrenaissance.dar.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Loan")
public class Loan {

    private int loanID;
    private Tournament tournament;
    private Card card;
    private User borrower;
    private User lender;
    private boolean returned;
    private boolean done;


    public Loan(Tournament tournament, Card card, User borrower, User lender, boolean returned, boolean done) {
        this.tournament = tournament;
        this.card = card;
        this.borrower = borrower;
        this.lender = lender;
        this.returned = returned;
        this.done = done;
    }

    public Loan(Tournament tournament, Card card, User borrower) {
        this.tournament = tournament;
        this.card = card;
        this.borrower = borrower;
        this.returned = false;
        this.done = false;
    }

    public Loan(){}

    @Id
    @Column(name="loanID")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    @ManyToOne
    @JoinColumn(name = "tournamentID",
            foreignKey = @ForeignKey(name = "FK_Loan_Tournament")
    )
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @ManyToOne
    @JoinColumn(name = "cardID",
            foreignKey = @ForeignKey(name = "FK_Loan_Card")
    )
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @ManyToOne
    @JoinColumn(name = "borrowerID",
            foreignKey = @ForeignKey(name = "FK_Loan_borrower")
    )
    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    @ManyToOne
    @JoinColumn(columnDefinition="integer", name = "lenderID",
            foreignKey = @ForeignKey(name = "FK_Loan_lender")
    )
    public User getLender() {
        return lender;
    }

    public void setLender(User lender) {
        this.lender = lender;
    }

    @Column(name="returned", nullable = false, columnDefinition = "boolean default false")
    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    @Column(name="done", nullable = false, columnDefinition = "boolean default false")
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Loan){
            return this.loanID == ((Loan) obj).loanID;
        }
        return false;
    }
}
