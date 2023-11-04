import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

// JavaFX Application main entry point
public class App extends Application {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";
    private static final String API_KEY = "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";
    private static final String TOKEN = "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";
    private static final String MODEL1 = "whisper-1";
    private static final String MODEL2 = "text-davinci-003";
    private static final String RESPONSE = "Your response is: ";
    
    public static void main(String[] args) {
        launch(args);
    }

    private void newScreen() {
        Stage inputStage = new Stage();

        VBox inputBox = new VBox();
        inputBox.setAlignment(Pos.TOP_CENTER);

        HBox inputPrompt = new HBox();
        inputPrompt.setAlignment(Pos.CENTER);
        inputPrompt.setPrefSize(500, 500);

        Label response = new Label("Please select your meal type: Breakfast, Lunch, or Dinner");
        response.setPrefSize(500, 500);
        response.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        response.setAlignment(Pos.CENTER);
        

        Button micButton = new Button("Record");
        micButton.setMinHeight(25.0);
        micButton.setAlignment(Pos.BOTTOM_CENTER);
        micButton.setOnAction(e -> {
            try {
                String voiceString = RESPONSE;
                voiceString += getVoiceInput();
                response.setText(voiceString);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        });
        
        inputPrompt.getChildren().add(response);
        
        Button doneButton = new Button("Done");
        doneButton.setMinHeight(25.0);
        doneButton.setAlignment(Pos.BOTTOM_CENTER);
        doneButton.setOnAction(e -> {
            try {

            } catch (Exception exc) {
                exc.printStackTrace();
            }
        });

        inputBox.getChildren().addAll(inputPrompt, micButton, doneButton);
        inputBox.setSpacing(15);

        Scene scene = new Scene(inputBox, 600, 600);

        inputStage.setTitle("Create Recipe");
        inputStage.setResizable(false);
        inputStage.setScene(scene);
        inputStage.show(); 
    }

    private String getVoiceInput()throws IOException, URISyntaxException{
        ChatGPT voiceInput = new ChatGPT();

        // Create file object from file path
        String path = "/Users/chaupham/Downloads/test.m4a";
        File file = new File(path);
        
        String result = voiceInput.handleVoiceInput(file, API_ENDPOINT, TOKEN, MODEL1);

        return result;
    }

    private String processVoiceInput()throws Exception{
        ChatGPT recipe = new ChatGPT();
        String result = recipe.processInput(API_ENDPOINT, API_KEY, MODEL2);
        return result;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Recipe Run");

        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.TOP_CENTER);

        // top titlebar
        {
            HBox titleHbox = new HBox();
            titleHbox.setAlignment(Pos.CENTER_RIGHT);
            Button newRecipe = new Button("New Recipe");
            newRecipe.setOnMouseClicked(e -> {
                newScreen();
            });
            newRecipe.setMinHeight(50.0);
            Region spacer = new Region();
            spacer.setMinWidth(50.0);
            titleHbox.getChildren().addAll(newRecipe, spacer);
            mainBox.getChildren().add(titleHbox);
        }

        VBox scrollPaneContents = new VBox();
        scrollPaneContents.setAlignment(Pos.TOP_CENTER);
        for (int i = 0; i < 20; i++) {
            StackPane recipePane = new StackPane();

            HBox.setHgrow(recipePane, Priority.ALWAYS);

            HBox recipeHbox = new HBox(20.0);
            recipePane.getChildren().add(recipeHbox);
            StackPane.setAlignment(recipeHbox, Pos.CENTER);
            recipeHbox.setStyle("-fx-border-color: black; -fx-border-width: 1;");

            recipeHbox.setMinHeight(100.0);

            {
                StackPane descPane = new StackPane();
                HBox.setHgrow(descPane, Priority.ALWAYS);

                BorderPane descInside = new BorderPane();
                descInside.setPadding(new Insets(20.0));
                descInside.setStyle("-fx-border-color: black; -fx-border-width: 1;");
                descPane.getChildren().add(descInside);

                // recipe title
                Label title = new Label("Recipe");
                title.setAlignment(Pos.CENTER_LEFT);
                BorderPane.setAlignment(title, Pos.CENTER_LEFT);
                descInside.setLeft(title);

                // recipe type
                Label recipeType = new Label("Breakfast");
                recipeType.setAlignment(Pos.CENTER_RIGHT);
                BorderPane.setAlignment(recipeType, Pos.CENTER_RIGHT);
                descInside.setRight(recipeType);

                recipeHbox.getChildren().add(descPane);
            }

            {
                StackPane delPane = new StackPane();
                delPane.getChildren().add(new Button("Delete"));
                delPane.setPadding(new Insets(20.0));
                recipeHbox.getChildren().add(delPane);
            }

            recipePane.setPadding(new Insets(20.0));

            scrollPaneContents.getChildren().add(recipePane);
        }
        // mainBox.getChildren().add(scrollPaneContents);
        ScrollPane pane = new ScrollPane();
        pane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            scrollPaneContents.setPrefWidth(newValue.getWidth() - 1);
        });
        pane.setContent(scrollPaneContents);
        mainBox.getChildren().add(pane);

        Scene scene = new Scene(mainBox, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}