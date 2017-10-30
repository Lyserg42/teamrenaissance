package fr.teamrenaissance.dar.entities;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Card")
public class Card {

    private static final int NAME_LENGTH = 30;
    private static final int URL_LENGTH = 300;

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

    @Id
    @Column(name="cardID")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    @Column(name = "name", length = NAME_LENGTH, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "picture", length = URL_LENGTH, nullable = false)
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Column(name = "isStandardLegal", nullable = false)
    public boolean getIsStandardLegal() {
        return isStandardLegal;
    }

    public void setIsStandardLegal(boolean isStandardLegal) {
        this.isStandardLegal = isStandardLegal;
    }

    @Column(name = "isModernLegal", nullable = false)
    public boolean getIsModernLegal() {
        return isModernLegal;
    }

    public void setIsModernLegal(boolean isModernLegal) {
        this.isModernLegal = isModernLegal;
    }

    @Column(name = "isLegacyLegal", nullable = false)
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Card){
            return this.cardID == ((Card) obj).cardID;
        }
        return false;
    }
}
