package fr.teamrenaissance.dar.test.Card;

import fr.teamrenaissance.dar.managers.CardManager;
import fr.teamrenaissance.dar.managers.UserManager;
import org.json.JSONObject;

public class SearchCard {
    public static void main(String args[]) {
        CardManager um = new CardManager();
        JSONObject obj1 = null;
        JSONObject obj2 = null;
        obj1 = um.searchCardbyId(1);
        obj2 = um.searchCardbyName("Lightning Bolt");
        System.out.println(obj1.toString());
        System.out.println(obj2.toString());

    }
}
