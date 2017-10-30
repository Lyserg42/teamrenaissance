package fr.teamrenaissance.dar.entities;

import javax.persistence.*;

@Entity
@Table(name = "Entry")
public class Entry {

    @EmbeddedId
    private EntryId id;
    private boolean participate;

    /*
    public Entry(Tournament tournament, User user, boolean participate) {
        this.tournament = tournament;
        this.user = user;
        this.participate = participate;
    }
    */

    public Entry(){}

    /*
    @Id
    @ManyToOne
    @JoinColumn(name = "tournamentID",
            foreignKey = @ForeignKey(name = "FK_Entry_Tournament")
    )
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "userID",
            foreignKey = @ForeignKey(name = "FK_Entry_User")
    )
    public User getUser() {
        return user;
    }

    public void setUser(User unser) {
        this.user = unser;
    }
    */

    public EntryId getId() {
        return id;
    }

    public void setId(EntryId id) {
        this.id = id;
    }

    @Column(name = "participate", nullable = false)
    public boolean isParticipate() {
        return participate;
    }

    public void setParticipate(boolean participate) {
        this.participate = participate;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Entry){
            return this.id.equals(((Entry) obj).id);
        }
        return false;
    }

    // hashCode
}
