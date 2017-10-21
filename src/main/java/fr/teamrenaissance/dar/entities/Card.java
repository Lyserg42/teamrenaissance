package fr.teamrenaissance.dar.db;
import java.util.concurrent.atomic.AtomicInteger;

public class Card {

    private static final AtomicInteger count = new AtomicInteger(0);

    private int idCard;
    private String name;
    private String picture;
    private byte isStandardLegal;
    private byte isModernLegal;
    private byte isLegacyLegal;

    public Card(String n, String p,byte s, byte m, byte l){
        this.idCard = count.incrementAndGet();
        this.name=n;
        this.picture=p;
        this.isStandardLegal=s;
        this.isModernLegal=m;
        this.isLegacyLegal=l;
    }

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public byte getIsStandardLegal() {
        return isStandardLegal;
    }

    public void setIsStandardLegal(byte isStandardLegal) {
        this.isStandardLegal = isStandardLegal;
    }

    public byte getIsModernLegal() {
        return isModernLegal;
    }

    public void setIsModernLegal(byte isModernLegal) {
        this.isModernLegal = isModernLegal;
    }

    public byte getIsLegacyLegal() {
        return isLegacyLegal;
    }

    public void setIsLegacyLegal(byte isLegacyLegal) {
        this.isLegacyLegal = isLegacyLegal;
    }

    @Override
    public String toString() {
        return "Card{" + this.idCard +"," +  this.name +"," + this.picture +", ("+this.isStandardLegal+","+this.isModernLegal+","+this.isLegacyLegal+") }";
    }
}
