package cse110_project;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.json.JSONObject;

public class RecipeStateHandler implements HttpHandler {
    RecipeStateManager state;
    private final String saveFilePath = "recipes_server.json";

    public RecipeStateHandler() {
        String savedData = "";
        try {
            savedData = new String(Files.readAllBytes(Paths.get(saveFilePath)));
        } catch (IOException e) {
            System.out.println("Couldn't find saved file " + saveFilePath + " , making new save data");
        }
        if (savedData.length() > 0) {
            System.out.println("Loading from " + saveFilePath);
            state = JSONOperations.fromJSONString(savedData);
        } else {
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

            try (FileWriter fw = new FileWriter(saveFilePath)) {
                fw.write(JSONOperations.intoJSONString(state));
            } catch (IOException e) {
                e.printStackTrace();
            }

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
