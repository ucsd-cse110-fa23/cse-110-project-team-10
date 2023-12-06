package cse110_project;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;

import org.json.JSONObject;

public class RecipeWebHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            RecipeQuery query = new RecipeQuery(exchange.getRequestURI());
            MongoDB_Account acc = new MongoDB_Account(App.mongoURL);
            RecipeStateManager thisState = acc.grabRecipeFromAccount(query.user);
            String response = query.renderHtml(thisState);
            exchange.sendResponseHeaders(200, response.length());
            OutputStream outStream = exchange.getResponseBody();
            outStream.write(response.getBytes());
            outStream.close();
        } catch (Exception e) {
            String response = "Failed to get recipe by web link: " + e.getLocalizedMessage() + "\n\n"
                    + e.getStackTrace();
            exchange.sendResponseHeaders(400, response.length());
            OutputStream outStream = exchange.getResponseBody();
            outStream.write(response.getBytes());
            outStream.close();
        }
    }

}

class RecipeQuery {
    String user, recipeName = "";

    RecipeQuery() {
    }

    String renderHtml(RecipeStateManager thisState) {
        Recipe r = thisState.getRecipeByName(recipeName);
        String response = "<html><body><p>Recipe name: " + r.getRecipeName() + "</p><p>Recipe description: "
                + r.getRecipeDescription() + "</p><img src=\"" + r.getRecipeImage() + "\"></img>";
        return response;
    }

    RecipeQuery(String u, String r) {
        user = u;
        recipeName = r;
    }

    RecipeQuery(URI uri) {
        String[] res = uri.toString().split("/");
        byte[] decodedBytesUser = Base64.getDecoder().decode(res[2]);
        String decodedUser = new String(decodedBytesUser);
        byte[] decodedBytesRecipe = Base64.getDecoder().decode(res[3]);
        String decodedRecipe = new String(decodedBytesRecipe);
        user = decodedUser;
        recipeName = decodedRecipe;
    }

    public String turnIntoURI() {
        String encodedUser = Base64.getEncoder().encodeToString(user.getBytes());
        String encodedRecipe = Base64.getEncoder().encodeToString(recipeName.getBytes());
        String result = "/recipeweb/" + encodedUser + "/" + encodedRecipe;
        return result;
    }

}