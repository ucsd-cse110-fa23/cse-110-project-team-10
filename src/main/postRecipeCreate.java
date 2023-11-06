package main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class postRecipeCreate extends VBox {
    
    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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";
    private static final String MODEL = "text-davinci-003";

    public String rName; 
    public String rDesc;

    private Button saveRecipeButton; 
    private Button editRecipeButton;
    private Button backButton; 
    private Label recipeDescription;
    private Stage postCreateStage;
    private Scene scene;

    // display the generated recipe description in a new popout window
    // pmt = passed meal type, pmd = passed meal description
    public postRecipeCreate(String pmt, String pmd) throws IOException, InterruptedException, URISyntaxException {
        String ro = recipeGenerate(pmt, pmd);
        rName = ro.substring(ro.indexOf(':')+2, ro.indexOf("Ingredients"));
        rDesc = ro.substring(ro.indexOf("Ingredients"));

        postCreateStage = new Stage();
        
        recipeDescription = new Label(ro);
        recipeDescription.setAlignment(Pos.CENTER);
        recipeDescription.setWrapText(true);

        saveRecipeButton = new Button("save");
        saveRecipeButton.setPrefSize(45, 15);
        saveRecipeButton.setAlignment(Pos.CENTER);

        editRecipeButton = new Button("edit");
        editRecipeButton.setPrefSize(45, 15);
        editRecipeButton.setAlignment(Pos.CENTER);

        backButton = new Button("back");
        backButton.setPrefSize(45, 15);
        backButton.setAlignment(Pos.CENTER);

        this.setPrefSize(500, 500);
    }

    public void postRecipeCreateDisplay() {
        saveRecipeButton.setOnAction(e -> {
            //todo
            System.out.println(rName);
            System.out.println(rDesc);
        });
        editRecipeButton.setOnAction(e -> {
            //todo
        });
        backButton.setOnAction(e -> {
            //todo
        });

        HBox buttonArea = new HBox();
        buttonArea.getChildren().addAll(saveRecipeButton, editRecipeButton, backButton);
        buttonArea.setPadding(new Insets(10, 10, 10, 10));
        buttonArea.setSpacing(199);

        VBox recipeDetail = new VBox();
        recipeDetail.getChildren().addAll(recipeDescription);
        recipeDetail.setPadding(new Insets(10, 10, 10, 10));

        this.getChildren().addAll(buttonArea, recipeDetail);

        scene = new Scene(this, 550, 550);

        postCreateStage.setTitle("Generated Recipe");
        postCreateStage.setResizable(false);
        postCreateStage.setScene(scene);
        postCreateStage.show();
    }
    
    public String recipeGenerate(String rt, String rd) throws IOException, InterruptedException, URISyntaxException{
        // Set request parameters
        String prompt = "Make me a "+rt+" meal with the following ingredients: "+rd+". Start response with the format:'recipe name:___' and response can be no longer than 500 words. Include the meal type breakfast/lunch/dinner in the format in parentheses immediately after meal name";
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