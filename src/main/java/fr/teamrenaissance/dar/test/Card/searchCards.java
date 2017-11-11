package fr.teamrenaissance.dar.test.Card;

import fr.teamrenaissance.dar.managers.CardManager;
import org.json.JSONArray;
import org.json.JSONObject;

public class searchCards {
    public static  void main(String [] args){
        JSONArray cards = new JSONArray();
        JSONObject res = new JSONObject();
        cards.put("Desert Nomads");
        cards.put("Pyramids");
        cards.put("Sandals of Abdallah");
        System.out.println(cards.toString());
        res = CardManager.searchCards(cards);
        System.out.print(res.toString());
    }
}
