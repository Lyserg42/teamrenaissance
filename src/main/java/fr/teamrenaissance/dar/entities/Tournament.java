package fr.teamrenaissance.dar.db;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Tournament {
    private static final AtomicInteger count = new AtomicInteger(0);

    private enum Type{
        GP,PT,RPTQ,OTHER}
    private enum Format{
        Standard, Modern,Limited,Legacy,Constructed, TeamModern, TeamLimited,Other
    }

    private int idTournament;
    private String place;
    private Date date;
    private Format format;
    private Type type;

    public Tournament(String p, Date d, Format f, Type t){
        this.idTournament=count.incrementAndGet();
        this.place=p;
        this.date=d;
        this.format=f;
        this.type=t;
    }

    public int getIdTournament() {
        return idTournament;
    }

    public void setIdTournament(int idTournament) {
        this.idTournament = idTournament;
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
        return "Tournament{" + this.idTournament + "," + this.place +"," + this.date +"," + this.format +"," +this.type +"}";
    }
}

