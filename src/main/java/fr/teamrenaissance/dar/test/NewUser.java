package fr.teamrenaissance.dar.test;

import fr.teamrenaissance.dar.managers.UserManager;
import org.json.JSONObject;

public class NewUser {

    public static void main(String args[]){
        UserManager um = new UserManager();
        JSONObject obj = null;
        try {
            obj = um.newUser("hellal","sarra","sarra_hellal@hotmail.com",
                    "sarrra","totototo",
                    "xxx","","555","","","");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(obj.toString());
    }
}
