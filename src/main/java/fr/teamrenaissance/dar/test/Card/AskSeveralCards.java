package fr.teamrenaissance.dar.test.Card;

import fr.teamrenaissance.dar.managers.CardManager;
import org.json.JSONObject;

public class AskSeveralCards {
    public static void main(String args[]) {
        CardManager um = new CardManager();
        JSONObject obj;
        obj =um.askSeveralCard("Grizzly Bears\nLightning Bolt");
        System.out.println(obj.toString());
    }
}
