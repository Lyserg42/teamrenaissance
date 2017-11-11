package fr.teamrenaissance.dar.test.User;

import fr.teamrenaissance.dar.managers.UserManager;
import org.json.JSONObject;

public class ConnectionUser {

    public static void main(String args[]){

        JSONObject obj = null;
        try {
            obj = UserManager.connectionUser("sarro","123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(obj.toString());
    }
}
