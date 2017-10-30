package fr.teamrenaissance.dar.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EntryId implements Serializable {

    private int tournamentID;
    private int userID;

    public EntryId(){
    }

    public EntryId(int tournamentID, int userID){
        this.tournamentID = tournamentID;
        this.userID = userID;
    }

    @Column(name = "tournamentID")
    public int getTournamentID() {
        return tournamentID;
    }

    @Column(name = "userID")
    public int getUserID() {
        return userID;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EntryId){
            return this.tournamentID == ((EntryId) obj).tournamentID
                    && this.userID == ((EntryId) obj).userID;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tournamentID, userID);
    }
}
