package fr.teamrenaissance.dar.entities;

import javax.persistence.*;

@Entity
@Table(name = "Entry")
public class Entry {

    @EmbeddedId
    private EntryId id;
    private boolean participate;


    public Entry(){}

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
