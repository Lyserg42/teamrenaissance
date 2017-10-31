package fr.teamrenaissance.dar.test;

import fr.teamrenaissance.dar.managers.UserManager;
import org.json.JSONException;
import org.json.JSONObject;

public class GetUser {

    public static void main(String args[]){
        UserManager um = new UserManager();
        JSONObject userExist = um.getUserJson(1);
        JSONObject userNotExist = um.getUserJson(6);

        System.out.println(userExist.toString());
        System.out.println(userNotExist.toString());
    }
}
