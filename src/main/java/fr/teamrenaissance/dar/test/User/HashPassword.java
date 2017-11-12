package fr.teamrenaissance.dar.test.User;

import fr.teamrenaissance.dar.managers.UserManager;

public class HashPassword {
    public static  void main (String args[]){
        String password = "toto";
        String pass = "toto";
        System.out.println("avant hash256 :"+password+" apres hash256 :"+
                UserManager.hash256(password));

        System.out.println("Les 2 hash sont ils identiquent pour un meme mot de passe donné ? "+
                UserManager.hash256(password).equals( UserManager.hash256(pass)));
    }
}
