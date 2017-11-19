package fr.teamrenaissance.dar.servlets;

import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * This class contains tools methods for servlets.
 */
public class ServletUtils {

    public static final String CONNECTION_NEEDED_MESSAGE = "Pour faire cela, vous devez vous connecter.";

    public static JSONObject getJsonFromRequest(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            reader.close();
        }

        try {
            JSONObject jsonObject =  new JSONObject(sb.toString());
            return jsonObject;
        } catch (JSONException e) {
            throw new IOException("Error parsing JSON request string : " + sb.toString());
        }
    }
}
