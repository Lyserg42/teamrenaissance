package fr.teamrenaissance.dar.test.User;

import fr.teamrenaissance.dar.managers.UserManager;
import org.json.JSONException;
import org.json.JSONObject;

public class ConnectionUser {

    public static void main(String args[]){

        JSONObject obj = null;
       // try {
            obj = UserManager.connectionUser("sarro","Oii7lllllllll");
            /*if(obj.has("userSuccesConnection")){
                System.out.println(obj.get("userSuccesConnection"));
            }*/

       /* } catch (JSONException j) {
            j.printStackTrace();
        }*/
        System.out.println(obj.toString());
    }
}
