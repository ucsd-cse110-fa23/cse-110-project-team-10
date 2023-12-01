package cse110_project;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

import org.bson.Document;

import java.nio.file.Paths;
import java.nio.file.Files;

import org.json.JSONObject;

public class RecipeStateHandler implements HttpHandler{
    private RecipeStateManager state;
    private MongoDB_Account mongodb;
    private String user;
    private final String URI = "mongodb+srv://zpliang:LoveMinatoAqua12315@violentevergarden.vm9uhtb.mongodb.net/?retryWrites=true&w=majority";
    private final String saveFilePath = "account.csv";

    public RecipeStateHandler() throws IOException{
        String savedData = "";
        // try {
        //     savedData = new String(Files.readAllBytes(Paths.get(saveFilePath)));
        // } catch (IOException e) {
        //     System.out.println("Couldn't find saved file " + saveFilePath + " , making new save data");
        // }
        // if (savedData.length() > 0) {
        //     System.out.println("Loading from " + saveFilePath);
        //     state = JSONOperations.fromJSONString(savedData);
        // } else {
        //     state = new RecipeStateManager();
        // }

        try{
            BufferedReader br = new BufferedReader(new FileReader(saveFilePath));
            savedData = br.readLine();
            user = savedData.split(",")[0];
            mongodb = new MongoDB_Account(URI);
            mongodb.grabRecipeFromAccount(user, state);
        }catch (Exception e) {
            state = new RecipeStateManager();
        }
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if (method.equals("GET")) {
            String response = JSONOperations.intoJSONString(state);
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream outStream = httpExchange.getResponseBody();
            outStream.write(response.getBytes());
            outStream.close();
        } else if (method.equals("POST")) {
            InputStream inputStream = httpExchange.getRequestBody();
            String requestBody = readInputStream(inputStream);
            state = JSONOperations.fromJSONString(requestBody);

            // try (FileWriter fw = new FileWriter(saveFilePath)) {
            //     fw.write(JSONOperations.intoJSONString(state));
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }    
            //Create an update document for recipe
            Document recipe = new Document("$set", Document.parse(JSONOperations.intoJSONString(state)));
            mongodb.updateRecipetoAccount(user, recipe);


            String response = "ok";
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream outStream = httpExchange.getResponseBody();
            outStream.write(response.getBytes());
            outStream.close();
        }
    }

    private String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

}
