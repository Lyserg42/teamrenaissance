package fr.teamrenaissance.dar.test;

import fr.teamrenaissance.dar.managers.UserManager;
import org.json.JSONObject;

public class AskCards {
    public static void main(String args[]) {
        UserManager um = new UserManager();
        JSONObject obj = null;
        obj = um.askCards(1);
        System.out.println(obj.toString());
        obj = um.askCards(2);
        System.out.println(obj.toString());
    }
}
