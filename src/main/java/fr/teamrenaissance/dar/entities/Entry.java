package fr.teamrenaissance.dar.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Entry")
public class Entry {

    private int tournamentID;
    private int unserID;
    private boolean participate;

    public Entry(int tournamentID, int unserID, boolean participate) {
        this.tournamentID = tournamentID;
        this.unserID = unserID;
        this.participate = participate;
    }

    public Entry(){}

    public int getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

    public int getUnserID() {
        return unserID;
    }

    public void setUnserID(int unserID) {
        this.unserID = unserID;
    }

    public boolean isParticipate() {
        return participate;
    }

    public void setParticipate(boolean participate) {
        this.participate = participate;
    }
}
