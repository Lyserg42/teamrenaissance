package fr.teamrenaissance.dar.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User")
public class User {

    private static final int NAME_LENGTH = 20;
    private static final int PASS_MIN_LENGTH = 6;
    private static final int EMAIL_LENGTH = 50;
    private static final int URL_LENGTH = 300;
    private static final int PHONE_LENGTH = 15;

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

    /**
     * Loans where the user is the lender.
     */
    private Set<Loan> lenderLoans = new HashSet<>();
    /**
     * Loans where the user is the borrower.
     */
    private Set<Loan> borrowerLoans = new HashSet<>();


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

    @Id
    @Column(name="userID")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Column(name = "name", length = NAME_LENGTH, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if(name == null) throw new Exception("Quel est votre nom ?");
        if(name.length() > NAME_LENGTH) throw new Exception("Désolé, le nom est limité à "+NAME_LENGTH+" caractères.");
        this.name = name;
    }

    @Column(name = "firstname", length = NAME_LENGTH, nullable = false)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) throws Exception {
        if(firstname == null) throw new Exception("Quel est votre prénom ?");
        if(firstname.length() > NAME_LENGTH) throw new Exception("Désolé, le prénom est limité à "+NAME_LENGTH+" caractères.");
        this.firstname = firstname;
    }

    @Column(name = "username", length = NAME_LENGTH, unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws Exception {
        if(username == null) throw new Exception("Choisissez un pseudo.");
        if(username.length() > NAME_LENGTH) throw new Exception("Désolé, le pseudo est limité à "+NAME_LENGTH+" caractères.");
        this.username = username;
    }

    @Column(name = "email", length = EMAIL_LENGTH, unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if(email == null) throw new Exception("Quelle est votre adresse email ?");
        if(!email.contains("@")) throw new Exception("Veuillez entrer une adresse email valide.");
        if(email.length() > EMAIL_LENGTH)
            throw new Exception("Désolé, l'adresse email est limité à "+EMAIL_LENGTH+" caractères.");
        this.email = email;
    }

    @Column(name = "password", length = NAME_LENGTH, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        if(password == null || password.length() < PASS_MIN_LENGTH)
            throw new Exception("Votre mot de passe doit contenir au moins 6 caractères.");
        if(password.length() > NAME_LENGTH)
            throw new Exception("Désolé, le mot de passe est limité à "+NAME_LENGTH+" caractères.");

        this.password = password;
    }

    @Column(name = "address", length = URL_LENGTH, nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws Exception {
        if(address == null) throw new Exception("Quelle est votre adresse ?");
        if(address.length() > URL_LENGTH)
            throw new Exception("L'adresse est limitée à "+URL_LENGTH+" caractères.");
        this.address = address;
    }

    @Column(name = "avatar", length = URL_LENGTH, nullable = true)
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Column(name = "dciNumber", length = PHONE_LENGTH, nullable = true)
    public String getDciNumber() {
        return dciNumber;
    }

    public void setDciNumber(String dciNumber) throws Exception {
        if(dciNumber != null && dciNumber.length() > PHONE_LENGTH)
            throw new Exception("Le numéro de DCI est limité à "+PHONE_LENGTH+" caractères.");
        this.dciNumber = dciNumber;
    }

    @Column(name = "phoneNumber", length = PHONE_LENGTH, nullable = false)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws Exception {
        if(phoneNumber == null) throw new Exception("Quel est votre numéro de téléphone ? " +
                "Les joueurs avec qui vous ferez des échanges en auront besoin pour vous contacter.");
        if(phoneNumber.length() > PHONE_LENGTH)
            throw new Exception("Le numéro de téléphone est limité à "+PHONE_LENGTH+" caractères.");
        if(!phoneNumber.matches("[+]?\\d*"))
            throw new Exception("Veuillez entrer un numéro de téléphone valide.");
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "facebook", length = URL_LENGTH, nullable = true)
    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) throws Exception {
        if(facebook != null && facebook.length() > URL_LENGTH)
            throw new Exception("L'URL du compte Facebook est limitée à "+URL_LENGTH+" caractères.");
        this.facebook = facebook;
    }

    @Column(name = "twitter", length = URL_LENGTH, nullable = true)
    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) throws Exception {
        if(twitter != null && twitter.length() > URL_LENGTH)
            throw new Exception("L'URL du compte Twitter est limitée à "+URL_LENGTH+" caractères.");
        this.twitter = twitter;
    }

    @OneToMany(mappedBy = "lender")
    public Set<Loan> getLenderLoans() {
        return lenderLoans;
    }

    public void setLenderLoans(Set<Loan> lenderLoans) {
        this.lenderLoans = lenderLoans;
    }

    @OneToMany(mappedBy = "borrower")
    public Set<Loan> getBorrowerLoans() {
        return borrowerLoans;
    }

    public void setBorrowerLoans(Set<Loan> borrowerLoans) {
        this.borrowerLoans = borrowerLoans;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            return this.userID == ((User) obj).userID;
        }
        return false;
    }
}
