package fr.teamrenaissance.dar.test.User;

import fr.teamrenaissance.dar.managers.UserManager;


public class PasswordEquals {
    public static void main(String args[]){
       boolean res = UserManager.isEqualsPassword("Sarra","123");
       System.out.println(res);
       res = UserManager.isEqualsPassword("Sarra","Kiuiui7uip");
        System.out.println(res);


    }
}
