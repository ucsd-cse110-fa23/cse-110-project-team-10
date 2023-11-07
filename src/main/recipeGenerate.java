package main;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;


public class recipeGenerate {

    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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";
    private static final String MODEL = "text-davinci-003";

    private String recipe_type;
    private String recipe_list;

    public recipeGenerate(String rt, String rl) {
        recipe_type = rt;
        recipe_list = rl;
    }

    public String generate() throws IOException, InterruptedException, URISyntaxException{
        // Set request parameters
        String prompt = "Make me a "+recipe_type+" meal with the following ingredients: "+recipe_list+". Start response with the format:'recipe name:___' and response can be no longer than 500 words. Include the meal type breakfast/lunch/dinner in the format in parentheses immediately after meal name";
        int maxTokens = 550;
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
        .uri(new URI(API_ENDPOINT))
        .header("Content-Type", "application/json")
        .header("Authorization", String.format("Bearer %s", API_KEY))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString())).build();
        // Send the request and receive the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // Process the response
        String responseBody = response.body();

        JSONObject responseJson = new JSONObject(responseBody);
        JSONArray choices = responseJson.getJSONArray("choices");
        String generatedText = choices.getJSONObject(0).getString("text");

        // Return generated recipe as string
        return generatedText;
    }
}
