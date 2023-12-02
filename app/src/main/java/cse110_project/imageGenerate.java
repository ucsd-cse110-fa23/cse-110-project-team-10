package cse110_project;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public class imageGenerate {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/images/generations";
    private static final String API_KEY = "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";
    private static final String MODEL = "dall-e-2";
    private String prompt = "";

    imageGenerate(String prompt){
        this.prompt = prompt;
    }

    public String generate() {
        try {
            // Set request parameters
            int n = 1;

            // Create a request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", MODEL);
            requestBody.put("prompt", prompt);
            requestBody.put("n", n);
            requestBody.put("size", "256x256");

            // Create the HTTP client
            HttpClient client = HttpClient.newHttpClient();

            // Create the request object
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_ENDPOINT))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            // Send the request and receive the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Process the response
            String responseBody = response.body();
            JSONObject responseJson = new JSONObject(responseBody);
             
            String generatedImageURL = responseJson.getJSONArray("data").getJSONObject(0).getString("url");
            return generatedImageURL;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "unable to generate image";
    }
}
