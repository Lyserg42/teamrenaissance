package fr.teamrenaissance.dar.test.User;

import fr.teamrenaissance.dar.managers.UserManager;
import org.json.JSONObject;

public class SetUserProfil {
    public static void main(String args[]){

        JSONObject obj = null;
        try {
            obj = UserManager.setUserProfil("hellal","sarra","sarra_hellal@hotmail.com",
                    "Sarra","Kiuiui7uip","ZZZ",
                    "https://wamiz.com/uploads/images/shutterstock_322496870.jpg","",
                    "1236445","yyyyyy","","","","");

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(obj.toString());
    }
}
