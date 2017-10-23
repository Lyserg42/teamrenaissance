package fr.teamrenaissance.dar.entities;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Card")
public class Card {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private int cardID;
    private String name;
    private String picture;
    private boolean isStandardLegal;
    private boolean isModernLegal;
    private boolean isLegacyLegal;

    public Card(String n, String p, boolean s, boolean m, boolean l){
        this.name=n;
        this.picture=p;
        this.isStandardLegal=s;
        this.isModernLegal=m;
        this.isLegacyLegal=l;
    }

    public Card(){}

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
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

    public boolean getIsStandardLegal() {
        return isStandardLegal;
    }

    public void setIsStandardLegal(boolean isStandardLegal) {
        this.isStandardLegal = isStandardLegal;
    }

    public boolean getIsModernLegal() {
        return isModernLegal;
    }

    public void setIsModernLegal(boolean isModernLegal) {
        this.isModernLegal = isModernLegal;
    }

    public boolean getIsLegacyLegal() {
        return isLegacyLegal;
    }

    public void setIsLegacyLegal(boolean isLegacyLegal) {
        this.isLegacyLegal = isLegacyLegal;
    }

    @Override
    public String toString() {
        return "Card{" + this.cardID +"," +  this.name +"," + this.picture +", ("+this.isStandardLegal+","+this.isModernLegal+","+this.isLegacyLegal+") }";
    }
}
