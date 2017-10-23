package fr.teamrenaissance.dar.entities;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Tournament")
public class Tournament {

    private enum Type{
        GP,PT,RPTQ,OTHER}
    private enum Format{
        Standard, Modern,Limited,Legacy,Constructed, TeamModern, TeamLimited,Other
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
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

    public int getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Tournament{" + this.tournamentID + "," + this.place +"," + this.date +"," + this.format +"," +this.type +"}";
    }
}

