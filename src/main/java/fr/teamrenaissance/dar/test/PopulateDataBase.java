package fr.teamrenaissance.dar.test;

import fr.teamrenaissance.dar.entities.Card;
import fr.teamrenaissance.dar.managers.CardManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class PopulateDataBase {

    public static void main(String[] args){
        try {
            //get all the catalog pages
            JSONObject page;
            String url = "https://api.scryfall.com/cards";

            do {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                // optional default is GET
                con.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                page = new JSONObject(response.toString());

                //insert all the cards of the page in the data base
                JSONArray data = page.getJSONArray("data");
                for(int i = 0; i < data.length(); i++){
                    JSONObject jsonCard = data.getJSONObject(i);
                    String cName = jsonCard.getString("name");

                    JSONObject images;
                    if(jsonCard.has("image_uris")) {
                        images = jsonCard.getJSONObject("image_uris");
                    } else {
                        //multi faces card
                        if(!jsonCard.has("card_faces")) continue;
                        cName = cName.split("//")[0].trim();
                        JSONArray faces = jsonCard.getJSONArray("card_faces");
                        JSONObject cardFace = faces.getJSONObject(0);
                        if(!cardFace.getString("name").equals(cName)){
                            cardFace = faces.getJSONObject(1);
                        }
                        images = cardFace.getJSONObject("image_uris");
                    }
                    String imageURI = images.getString("normal");
                    JSONObject legalities = jsonCard.getJSONObject("legalities");
                    boolean standard = legalities.getString("standard").equals("legal");
                    boolean modern = legalities.getString("modern").equals("legal");
                    boolean legacy = legalities.getString("legacy").equals("legal");

                    Card card = new Card(cName, imageURI, standard, modern, legacy);
                    CardManager.insertCard(card);
                }

                //get the next page
                url = page.getString("next_page");
                TimeUnit.SECONDS.sleep(1);
                System.out.println(url);

           }while(page.getString("has_more").equals("true"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
