import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.io.*;
import java.net.*;
import org.json.*;
public class ChatGPT {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";
    private static final String API_KEY = "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";
    private static final String MODEL = "text-davinci-003";

    public String processInput()throws Exception{
        // Set request parameters
        String prompt = "";
        int maxTokens = 100;
        
        // for(int i = 1; i < args.length; i++)
        // {
        //     prompt += args[i];
        // }
        // int maxTokens = Integer.parseInt(args[0]);
        
        
        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 1.0);

        // Create the HTTP Client


        HttpClient client = HttpClient.newHttpClient();


        // Create the request object
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT))
        .header("Content-Type", "application/json")
        .header("Authorization", String.format("Bearer %s", API_KEY))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();


        // Send the request and receive the response
        HttpResponse<String> response = client.send(
        request,
        HttpResponse.BodyHandlers.ofString()
        );

        // Process the response
        String responseBody = response.body();

        JSONObject responseJson = new JSONObject(responseBody);


        JSONArray choices = responseJson.getJSONArray("choices");
        String generatedText = choices.getJSONObject(0).getString("text");


        // System.out.println(generatedText);
        return generatedText;

    }
}
