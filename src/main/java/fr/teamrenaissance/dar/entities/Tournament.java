package fr.teamrenaissance.dar.entities;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Tournament")
public class Tournament {

    private static final int MAX_LENGTH = 20;

    private enum Type{
        GP,PT,RPTQ,OTHER}
    private enum Format{
        Standard, Modern,Limited,Legacy,Constructed, TeamModern, TeamLimited,Other;
    }

    private int tournamentID;
    private String place;
    private Date date;
    private Format format;
    private Type type;

    public Tournament(String p, Date d, Format f, Type t){
        this.place=p;
        this.date=d;
        this.format=f;
        this.type=t;
    }

    public Tournament(){}

    @Id
    @Column(name="tournamentID")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public int getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

    @Column(name = "place", length = MAX_LENGTH, nullable = false)
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "format", length = MAX_LENGTH, nullable = false)
    @Enumerated(EnumType.STRING)
    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    @Column(name = "type", length = MAX_LENGTH, nullable = false)
    @Enumerated(EnumType.STRING)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Transient
    public String getName(){
        return this.type + " " + this.place;
    }

    @Override
    public String toString() {
        return "Tournament{" + this.tournamentID + "," + this.place +"," + this.date +"," + this.format +"," +this.type +"}";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Tournament){
            return this.tournamentID == ((Tournament) obj).tournamentID;
        }
        return false;
    }
}

