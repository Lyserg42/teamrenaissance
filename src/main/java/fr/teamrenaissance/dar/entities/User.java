package fr.teamrenaissance.dar.db;

import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private static final AtomicInteger count = new AtomicInteger(0);

    private int idUser;
    private String nom;
    private String prenom;
    private String pseudo;
    private String email;
    private String password;
    private String adresse;
    private String avatar;

    public User(String n, String p, String ps, String m, String pass, String ad, String av){
        this.nom=n;
        this.prenom=p;
        this.pseudo=ps;
        this.email=m;
        this.password=pass;
        this.adresse=ad;
        this.avatar=av;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
