package cse110_project;


import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;

import org.json.JSONObject;

public class RecipeWebHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'handle'");

        String response = exchange.getRequestURI().toString();
        exchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = exchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }


    
}

class RecipeQuery {
    String user, recipeName = "";

    RecipeQuery(){
        
    }

    RecipeQuery(String u, String r){
        user = u;
        recipeName = r;
    }

    public String fromRecipeQueryIntoURI (){
        String encodedUser = Base64.getEncoder().encodeToString(user.getBytes());
        String encodedRecipe = Base64.getEncoder().encodeToString(recipeName.getBytes());
        String result = "/recipeweb/" + encodedUser + "/" + encodedRecipe;
        return result;
    }

    public void fromURIToRecipeQuery (String uri){
        String[] res = uri.split("/");
        byte[] decodedBytesUser = Base64.getDecoder().decode(res[1]);
        String decodedUser = new String(decodedBytesUser);
        byte[] decodedBytesRecipe = Base64.getDecoder().decode(res[2]);
        String decodedRecipe = new String(decodedBytesRecipe);
        user = decodedUser;
        recipeName = decodedRecipe;

    }
}