package fr.teamrenaissance.dar.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private int userID;
    private String name;
    private String firstname;
    private String username;
    private String email;
    private String password;
    private String address;
    private String avatar;
    private String dciNumber;
    private String phoneNumber;
    private String facebook;
    private String twitter;

    public User(String n, String p, String ps, String m, String pass, String ad, String av, String dci, String tel,
                String fb, String tw){
        this.name=n;
        this.firstname=p;
        this.username=ps;
        this.email=m;
        this.password=pass;
        this.address=ad;
        this.avatar=av;
        this.dciNumber = dci;
        this.phoneNumber = tel;
        this.facebook = fb;
        this.twitter = tw;
    }

    public User(){}

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDciNumber() {
        return dciNumber;
    }

    public void setDciNumber(String dciNumber) {
        this.dciNumber = dciNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
}
