package fr.teamrenaissance.dar.test.User;

import fr.teamrenaissance.dar.managers.UserManager;
import org.json.JSONObject;

public class NewUser {

    public static void main(String args[]){
        JSONObject obj = null;
        try {
            obj = UserManager.newUser("hellal","sarra","sarra_hellal@hotmail.com",
                    "Sarra","Kiuiui7uip",
                    "rue des alouettes","","","","","","fontenay", "","94120");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(obj.toString());
    }
}
