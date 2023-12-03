/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package cse110_project;

import java.io.*;
import java.util.*;
import org.json.JSONObject;
import java.net.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
//import org.w3c.dom.Text;
import javafx.scene.text.*;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.nio.file.Paths;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class postRecipeCreate extends VBox {

    public String rName;
    public String rDesc;
    public RecipeKind rKind;

    private Button saveRecipeButton;
    private Button editRecipeButton;
    private Button backButton;
    private TextArea recipeDescription;
    private Stage postCreateStage;
    private Scene scene;
    private boolean editflag;

    // display the generated recipe description in a new popout window
    // pmt = passed meal type, pml = passed meal ingredient list
    public postRecipeCreate(String pmt, String pml) {
        JSONObject toSend = new JSONObject();
        toSend.put("pmt", pmt);
        toSend.put("pml", pml);
        try {
            HttpClient client = HttpClient.newHttpClient();
            // Create the request object
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(App.serverURL + "/genrecipe"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(toSend.toString())).build();
            // Send the request and receive the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Process the response
            String responseBody = response.body();
            JSONObject responseJson = new JSONObject(responseBody);
            rName = responseJson.getString("name");
            rDesc = responseJson.getString("desc");
            rKind = RecipeKind.valueOf(responseJson.getString("kind"));
        } catch (Exception e) {
            System.err.println("Failed to generate");
            e.printStackTrace();
        }

        postCreateStage = new Stage();
        recipeDescription = new TextArea();
        recipeDescription.setWrapText(true);
        recipeDescription.appendText(rDesc);
        editflag = false;
        recipeDescription.setEditable(editflag);

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

    public void postRecipeCreateDisplay(App app) {
        saveRecipeButton.setOnAction(e -> {
            String updatedDesc = recipeDescription.getText();
            Recipe newRecipe = new Recipe(rName, updatedDesc, rKind);

            app.getState().addRecipe(newRecipe);
            app.modifyUI();
            app.writeServerState();

            postCreateStage.close();
        });
        editRecipeButton.setOnAction(e -> {
            if (editflag == false) {
                editflag = true;
                recipeDescription.setEditable(editflag);
            } else {
                editflag = false;
                recipeDescription.setEditable(editflag);
            }
        });
        backButton.setOnAction(e -> {
            postCreateStage.close();
        });

        HBox buttonArea = new HBox();
        buttonArea.getChildren().addAll(saveRecipeButton, editRecipeButton, backButton);
        buttonArea.setPadding(new Insets(10, 10, 10, 10));
        buttonArea.setSpacing(199);

        VBox recipeDetail = new VBox();
        recipeDetail.getChildren().addAll(recipeDescription);
        recipeDetail.setPadding(new Insets(10, 10, 10, 10));

        ScrollPane sp = new ScrollPane(recipeDetail);
        sp.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

        this.getChildren().addAll(buttonArea, sp);

        scene = new Scene(this, 550, 550);

        postCreateStage.setTitle("Generated Recipe");
        postCreateStage.setResizable(false);
        postCreateStage.setScene(scene);
        postCreateStage.show();
    }
}

// This class creates prompts for user input
class Prompt extends HBox {
    private Label prompt;
    private Button micButton;
    private boolean isRecord;

    Prompt() {
        prompt = new Label();
        prompt.setPrefSize(500, 250);
        prompt.setWrapText(true);
        prompt.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        prompt.setAlignment(Pos.CENTER);

        micButton = new Button("Record");
        micButton.setMinHeight(250);

        this.getChildren().addAll(prompt, micButton);
        this.setAlignment(Pos.CENTER);
    }

    public void toggleRecord() {
        if (!isRecord) {
            isRecord = true;
        } else {
            isRecord = false;
        }
    }

    public Button getMicButton() {
        return this.micButton;
    }

    public void setLabel(String text) {
        prompt.setText(text);
    }

    public boolean getRecordStatus() {
        return isRecord;
    }
}

class newScreen extends VBox {

    private static final String RESPONSE = "Your response is: ";
    private static final String MEAL_PROMPT = "Please select your meal type: Breakfast, Lunch, or Dinner";
    private static final String INGREDIENT_PROMPT = "Please list the ingredients you have";
    private static final String ERROR_PROMPT = "Sentence doesn't contain mealtype, please retry!";

    private Prompt mealPrompt;
    private Prompt ingredientPrompt;

    private Button mealTypeMicButton;
    private Button ingredientMicButton;
    private Button doneButton;

    private Label recordingLabel;

    private AudioRecord aRecord;

    private Stage inputStage;

    private Scene scene;

    private postRecipeCreate prc;

    private String mealType = "";
    private String mealList = "";

    String defaultLabelStyle = "-fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px; -fx-text-fill: red; visibility: hidden";

    newScreen() {
        inputStage = new Stage();

        recordingLabel = new Label("Recording...");
        recordingLabel.setAlignment(Pos.BOTTOM_CENTER);
        recordingLabel.setStyle(defaultLabelStyle);

        aRecord = new AudioRecord(recordingLabel);

        this.setAlignment(Pos.TOP_CENTER);
        this.setPrefSize(500, 800);

        doneButton = new Button("Done");
        doneButton.setDisable(true);

        mealPrompt = new Prompt();
        ingredientPrompt = new Prompt();

        mealTypeMicButton = mealPrompt.getMicButton();
        ingredientMicButton = ingredientPrompt.getMicButton();
    }

    public void voiceInputScreen(App app) {
        mealPrompt.setLabel(MEAL_PROMPT);

        // Record and display user's response for meal type
        mealTypeMicButton.setOnAction(e -> {
            try {
                mealPrompt.toggleRecord();
                if (mealPrompt.getRecordStatus())
                    aRecord.startRecording();
                else {
                    aRecord.stopRecording();
                    mealType = getVoiceInput();
                    // checking if user mention any of the mealtype
                    if (mealType.toLowerCase().contains("breakfast")) {
                        mealType = "breakfast";
                    } else if (mealType.toLowerCase().contains("lunch")) {
                        mealType = "lunch";
                    } else if (mealType.toLowerCase().contains("dinner")) {
                        mealType = "dinner";
                    } else {
                        mealType = "";
                    }

                    if (mealType != "") {
                        mealPrompt.setLabel(RESPONSE + mealType);
                    } else {
                        mealPrompt.setLabel(ERROR_PROMPT);
                    }

                    // Prompt user to input ingredient list after finish recording meal type
                    ingredientPrompt.setLabel(INGREDIENT_PROMPT);
                    if (mealType != "" && mealList != "") {
                        doneButton.setDisable(false);
                    }
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        });

        // Record and display user's response for ingredient list
        ingredientMicButton.setOnAction(e -> {
            try {
                ingredientPrompt.toggleRecord();
                if (ingredientPrompt.getRecordStatus())
                    aRecord.startRecording();
                else {
                    aRecord.stopRecording();
                    mealList = getVoiceInput();
                    ingredientPrompt.setLabel(RESPONSE + mealList);
                    if (mealType != "" && mealList != "") {
                        doneButton.setDisable(false);
                    }
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        });

        // Generate and display recipe page
        doneButton.setOnAction(e -> {
            try {
                prc = new postRecipeCreate(mealType, mealList);
                prc.postRecipeCreateDisplay(app);
                inputStage.close();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        });

        this.getChildren().addAll(mealPrompt, ingredientPrompt, doneButton, recordingLabel);
        this.setSpacing(15);

        scene = new Scene(this, 600, 600);

        inputStage.setTitle("Create Recipe");
        inputStage.setResizable(false);
        inputStage.setScene(scene);
        inputStage.show();
    }

    // Helper method to write a file to the output stream in multipart form data
    // format
    private static void writeFileToOutputStream(OutputStream outputStream,
            File file, String boundary) throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(
                ("Content-Disposition: form-data; name=\"file\"; filename=\"" +
                        file.getName() + "\"\r\n").getBytes());
        outputStream.write(("Content-Type: audio/mpeg\r\n\r\n").getBytes());

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        fileInputStream.close();
    }

    private String getVoiceInput() throws Exception {
        Whisper voiceInput = new Whisper();
        String path = "recording.wav";
        File file = new File(path);

        URL url = new URI(App.serverURL + "/whisper").toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Set up request headers
        String boundary = "Boundary-" + System.currentTimeMillis();
        connection.setRequestProperty(
                "Content-Type",
                "multipart/form-data; boundary=" + boundary);

        // Set up output stream to write request body
        OutputStream outputStream = connection.getOutputStream();

        // Write file parameter to request body
        writeFileToOutputStream(outputStream, file, boundary);

        // Write closing boundary to request body
        outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());

        // Flush and close output stream
        outputStream.flush();
        outputStream.close();

        // Get response code
        int responseCode = connection.getResponseCode();

        String result = "There was an internal error";
        // Check response code and handle response accordingly
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String response = readStream(connection.getInputStream());
            JSONObject responseJson = new JSONObject(response);
            result = responseJson.getString("text");
        } else {
            String errorResponse = readStream(connection.getErrorStream());
            System.out.println("Failed to generate: " + errorResponse);
        }

        // Disconnect connection
        connection.disconnect();

        return result;
    }

    private String readStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }
}

class DetailedViewScreen extends VBox {

    private Button saveRecipeButton;
    private Button editRecipeButton;
    private Button backButton;
    private TextArea recipeDescription;
    private Stage postCreateStage;
    private Scene scene;
    private boolean saveflag;
    private Recipe tempR;

    // display the generated recipe description in a new popout window
    // pmt = passed meal type, pml = passed meal ingredient list
    DetailedViewScreen(Recipe r) {
        tempR = r;
        postCreateStage = new Stage();
        recipeDescription = new TextArea();
        recipeDescription.setWrapText(true);
        recipeDescription.appendText(tempR.getRecipeDescription());
        saveflag = false;
        recipeDescription.setEditable(saveflag);

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

    public void ShowDetailedView(App app) {
        saveRecipeButton.setOnAction(e -> {
            String updatedDesc = recipeDescription.getText();
            tempR.setRecipeDescription(updatedDesc);
            app.writeServerState();

            postCreateStage.close();
        });
        editRecipeButton.setOnAction(e -> {
            if (saveflag == false) {
                saveflag = true;
                recipeDescription.setEditable(saveflag);
            } else {
                saveflag = false;
                recipeDescription.setEditable(saveflag);
            }
        });
        backButton.setOnAction(e -> {
            postCreateStage.close();
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
}

class FilterUI extends HBox {
    private ComboBox<String> filterBox;
    private Label label;

    FilterUI() {
        label = new Label("Filter");
        label.setStyle("-fx-font-size:15;-fx-pref-height: 50px;-fx-pref-width: 40px");

        filterBox = new ComboBox<>();
        filterBox.setMinHeight(50.0);
        filterBox.getItems().addAll(
                "Default",
                "Breakfast",
                "Lunch",
                "Dinner"
        );
        filterBox.setValue("Default");
        this.getChildren().addAll(label, filterBox);
    }

    public ComboBox<String> getBox() {
        return filterBox;
    }
}

class SortUI extends HBox {
    private ComboBox<String> sortBox;
    private Label label;

    SortUI() {
        label = new Label("Sort");
        label.setStyle("-fx-font-size:15;-fx-pref-height: 50px;-fx-pref-width: 40px");

        sortBox = new ComboBox<>();
        sortBox.setMinHeight(50.0);
        sortBox.getItems().addAll(
                "Default: Most Recent",
                "Least Recent",
                "A-Z",
                "Z-A"
        );
        sortBox.setValue("Default: Most Recent");
        this.getChildren().addAll(label, sortBox);
    }

    public ComboBox<String> getBox() {
        return sortBox;
    }
}

interface ServerConnectionSituation {
    void doServerStuff() throws Exception; // if there was an exception that means the request didn't go through
}

// JavaFX Application main entry point
public class App extends Application {

    public static final String serverURL = "http://127.0.0.1:8100";

    private String rName;
    private String rDesc;

    private RecipeStateManager state;
    private Server server = new Server();
    private newScreen ns;

    private Stage primaryStage;

    private DetailedViewScreen ds;
    private RecipeKind rKind;
    public VBox mainBox;
    public VBox recipesUI;

    private FilterUI filterUI = new FilterUI();
    private ComboBox<String> filterBox;

    private SortUI sortUI = new SortUI();
    private ComboBox<String> sortBox;

    private Modify m;
    private RecipeStateManager modifiedList;
    private String sortCategory;
    private String mealType;

    public static void main(String[] args) {
        launch(args);
    }

    RecipeStateManager getState() {
        return state;
    }

    @Override
    public void start(Stage primaryStage) {
        server.startServer();
        primaryStage.setTitle("Recipe Run");

        this.primaryStage = primaryStage;

        LoginScreen login = new LoginScreen(this);
        mainBox = new VBox();
        mainBox.setAlignment(Pos.TOP_CENTER);
        setupTitleBar(mainBox);

        recipesUI = new VBox();
        updateFromServerState();
        setupRecipeUI(mainBox);

        Scene scene = new Scene(login, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        login.autoLogin();
    }

    public void thereWasAServerError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Failed to connect to the server");
        alert.setHeaderText("Failed to connect to the server");
        alert.setContentText(
                "The server could be down, your internet might be broken, or there may be a solar flare ravaging civilization.");
        alert.showAndWait();
    }

    public static boolean detectServerError(ServerConnectionSituation server) {
        try {
            server.doServerStuff();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void updateFromServerState() {
        ServerConnectionSituation situation = () -> {
            HttpClient client = HttpClient.newHttpClient();
            // Create the request object
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(App.serverURL + "/recipestate"))
                    .header("Content-Type", "application/json")
                    .GET().build();
            // Send the request and receive the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            state = JSONOperations.fromJSONString(responseBody);

            filterBox = filterUI.getBox();
            sortBox = sortUI.getBox();
            sortCategory = "default: most recent";
            mealType = "default";
            
            filterBox.setOnAction(e->{
                mealType = filterBox.getValue().toLowerCase();
                modifyUI();
            });

            sortBox.setOnAction(e->{
                sortCategory = sortBox.getValue().toLowerCase();
                modifyUI();
            });

            for (Recipe r : state.getRecipes()) {
                addRecipeUI(r);
            }
        };
        if(!detectServerError(situation)) {
            System.err.println("Failed to update from remote state");
            thereWasAServerError();
            state = new RecipeStateManager();
        }
    }
    
    //update UI after filter and sort
    public void modifyUI(){
        resetRecipeUI();
        m = new Modify(state);
        modifiedList = m.modify(mealType, sortCategory);
        if(sortCategory.equals("a-z") || sortCategory.equals("z-a")){
            Collections.reverse(modifiedList.getRecipes());
        }
        for (Recipe r : modifiedList.getRecipes()) {
            addRecipeUI(r);
        }
    }

    public void writeServerState() {
        boolean succeeded = false;
        while (!succeeded) {
            String toSend = JSONOperations.intoJSONString(state);
            try {
                HttpClient client = HttpClient.newHttpClient();
                // Create the request object
                HttpRequest request = HttpRequest
                        .newBuilder()
                        .uri(new URI(App.serverURL + "/recipestate"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(toSend.toString())).build();
                // Send the request and receive the response
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                succeeded = true;
            } catch (Exception e) {
                System.err.println("Failed to save remote state");
                // e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        server.stopServer();
    }

    public void transitionToMainScreen() {
        Scene scene = new Scene(mainBox, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupTitleBar(VBox mainBox) {
        HBox titleHbox = new HBox();
        titleHbox.setAlignment(Pos.CENTER_RIGHT);
        Button newRecipe = new Button("New Recipe");
        newRecipe.setMinHeight(50.0);

        newRecipe.setOnMouseClicked(e -> {
            ns = new newScreen();
            ns.voiceInputScreen(this);
        });

        Region spacer = new Region();
        spacer.setMinWidth(50.0);
        titleHbox.setSpacing(15);
        titleHbox.getChildren().addAll(sortUI, filterUI, newRecipe, spacer);
        mainBox.getChildren().add(titleHbox);
    }

    private void setupRecipeUI(VBox mainBox) {
        recipesUI.setAlignment(Pos.TOP_CENTER);
        ScrollPane pane = new ScrollPane();
        pane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            recipesUI.setPrefWidth(newValue.getWidth() - 1);
        });
        pane.setContent(recipesUI);
        mainBox.getChildren().add(pane);
    }

    public void addRecipeUI(Recipe recipe) {
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
            Button title = new Button(recipe.getRecipeName());
            title.setAlignment(Pos.CENTER_LEFT);
            BorderPane.setAlignment(title, Pos.CENTER_LEFT);
            descInside.setLeft(title);
            title.setOnMouseClicked(e -> {
                ds = new DetailedViewScreen(recipe);
                ds.ShowDetailedView(this);
                System.out.println(recipe.getRecipeDescription());
            });

            // recipe type
            Label recipeType = new Label(recipe.getRecipeKind().name());
            recipeType.setAlignment(Pos.CENTER_RIGHT);
            BorderPane.setAlignment(recipeType, Pos.CENTER_RIGHT);
            descInside.setRight(recipeType);

            recipeHbox.getChildren().add(descPane);
        }

        {
            StackPane delPane = new StackPane();
            Button deleteButton = new Button("Delete");
            delPane.getChildren().add(deleteButton);
            deleteButton.setOnMouseClicked(e -> {
                state.deleteRecipe(recipe);
                recipesUI.getChildren().remove(recipePane);
                writeServerState();
            });
            delPane.setPadding(new Insets(20.0));
            recipeHbox.getChildren().add(delPane);
        }

        recipePane.setPadding(new Insets(20.0));

        recipesUI.getChildren().add(0, recipePane);
    }

    public void resetRecipeUI(){
        recipesUI.getChildren().clear();
    }
}
// public class App extends Application {

// private newScreen ns;
// private DetailedViewScreen ds;
// public static final String serverURL = "http://127.0.0.1:8100";

// public static void main(String[] args) {
// launch(args);
// }

// private RecipeStateManager state;

// RecipeStateManager getState() {
// return state;
// }

// public VBox recipesUI;

// private String rName;
// private String rDesc;
// private RecipeKind rKind;
// private Server server = new Server();

// @Override
// public void start(Stage primaryStage) {
// server.startServer();
// primaryStage.setTitle("Recipe Run");

// VBox mainBox = new VBox();
// mainBox.setAlignment(Pos.TOP_CENTER);

// // top titlebar
// {
// HBox titleHbox = new HBox();
// titleHbox.setAlignment(Pos.CENTER_RIGHT);
// Button newRecipe = new Button("New Recipe");
// newRecipe.setMinHeight(50.0);

// newRecipe.setOnMouseClicked(e -> {
// ns = new newScreen();
// ns.voiceInputScreen(this);
// });

// Region spacer = new Region();
// spacer.setMinWidth(50.0);
// titleHbox.getChildren().addAll(newRecipe, spacer);
// mainBox.getChildren().add(titleHbox);
// }

// recipesUI = new VBox();

// updateFromServerState();

// recipesUI.setAlignment(Pos.TOP_CENTER);
// // mainBox.getChildren().add(scrollPaneContents);
// ScrollPane pane = new ScrollPane();
// pane.viewportBoundsProperty().addListener((observable, oldValue, newValue) ->
// {
// recipesUI.setPrefWidth(newValue.getWidth() - 1);
// });
// pane.setContent(recipesUI);
// mainBox.getChildren().add(pane);

// Scene scene = new Scene(mainBox, 1280, 720);
// primaryStage.setScene(scene);
// primaryStage.show();
// }
// public void LoginSuccess(){
// Stage stage = (Stage) mainBox.getScene().getWindow();
// Scene scene = new Scene(mainBox, 1280, 720);
// stage.setScene(scene);
// stage.show();
// }
