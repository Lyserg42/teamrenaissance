package fr.teamrenaissance.dar.test.User;

import fr.teamrenaissance.dar.managers.UserManager;
import org.json.JSONException;
import org.json.JSONObject;

public class GetUser {

    public static void main(String args[]){

        JSONObject userExist = UserManager.getUser("sarro");
        JSONObject userNotExist = UserManager.getUser("sarra");

        System.out.println(userExist.toString());
        System.out.println(userNotExist.toString());

    }
}
