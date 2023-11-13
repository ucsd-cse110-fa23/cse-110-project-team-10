package cse110_project;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;

import org.json.JSONObject;

public class RecipeRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String response = "Internal error";
        if (!method.equals("POST")) {
            httpExchange.sendResponseHeaders(500, response.length());
            OutputStream outStream = httpExchange.getResponseBody();
            outStream.write(response.getBytes());
            outStream.close();
            return;
        }

        InputStream inputStream = httpExchange.getRequestBody();
        String requestBody = readInputStream(inputStream);

        // Parse request body into JSON object
        JSONObject jsonObject = new JSONObject(requestBody);

        recipeGenerate rg = new recipeGenerate(jsonObject.getString("pmt"), jsonObject.getString("pml"));

        boolean done = false;
        String rName = "";
        String rDesc = "";
        String rKind = "";
        while (!done) {
            try {
                String ro = rg.generate();
                rName = ro.substring(ro.indexOf(':') + 2, ro.indexOf('('));
                rDesc = ro.substring(ro.indexOf("Ingredients"));
                rKind = ro.substring(ro.indexOf('(') + 1, ro.indexOf(')')).toLowerCase().strip();
                RecipeKind asKind = RecipeKind.valueOf(rKind); // checks if it's valid, throws exception if not
                done = true;
            } catch (Exception e) {
                System.out.println("The AI produced invalid response, trying again: " + e);
            }
        }

        JSONObject toReturn = new JSONObject();
        toReturn.put("name", rName);
        toReturn.put("desc", rDesc);
        toReturn.put("kind", rKind);

        response = toReturn.toString();

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
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
