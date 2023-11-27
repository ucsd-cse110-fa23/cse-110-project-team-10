package cse110_project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.text.*;

import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.*;
import java.net.*;
import java.util.*;

// class AccJSON {
//     //create account JSON object
//     public static JSONObject intoJSON(String user, String pass) {
//         JSONObject acc = new JSONObject();
//         acc.put("username", user);
//         acc.put("password", pass);
//         return acc;
//     }

//     //write accounts to JSON file
//     public static void writeToJSON(String jsonString, JSONObject acc){
//         JSONArray arr = new JSONArray(jsonString);
        
//         try {
//             arr = new JSONArray(jsonString);
//         } catch (Exception e) {
//             // if not a valid JSON array, initialize array
//             arr = new JSONArray();
//         }

//         arr.put(acc);

//         try(FileWriter fw = new FileWriter("accounts.json")){
//             fw.write(arr.toString());
//             fw.flush();
//             fw.close();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     //method to check duplicate usernames
//     public static boolean checkAcc(String jsonString, String user){
//         if(jsonString.length() > 2){
//             JSONArray arr = new JSONArray(jsonString);
//             for(int i = 0; i < arr.length(); i++){
//                 JSONObject acc = arr.getJSONObject(i);
//                 if(user.equals(acc.getString("username"))){
//                     return false;
//                 }
//             }
//         }
//         else{
//             return true;
//         }
//         return true;
//     }
// }


class Info extends HBox{
    private Label infoLabel;
    private TextField userInfo;

    Info(String label){
        infoLabel = new Label(label);

        userInfo = new TextField();
        userInfo.setPrefSize(380, 20);
        userInfo.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        userInfo.setPadding(new Insets(10, 0, 10, 0));
        
        this.getChildren().addAll(infoLabel, userInfo);
        this.setAlignment(Pos.CENTER);
    }

    public TextField getUserInfo(){
        return userInfo;
    }
}

class CreateBox extends VBox {
    private Info username;
    private Info password;
    private Info confirmPass;

    private Label errorMessage;
    private boolean isMatch;

    CreateBox(){
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(5);

        username = new Info("Username: ");
        password = new Info("Password: ");
        confirmPass = new Info("Confirm Password: ");

        errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red");

        isMatch = false;

        this.getChildren().addAll(username, password, confirmPass, errorMessage);
        this.setAlignment(Pos.CENTER);
    }

    public void checkPass(){
        if(password.getUserInfo().getText().equals(confirmPass.getUserInfo().getText())){
            isMatch = true;
        }
        else{
            isMatch = false;
        }
    }

    public boolean passMatch(){
        return isMatch;
    }

    public String userInfo(){
        return username.getUserInfo().getText();
    }

    public String passInfo(){
        return password.getUserInfo().getText();
    }

    public void setErrorMsg(String prompt){
        errorMessage.setText(prompt);
    }
}

class Footer extends HBox {

    private Button createAccButton;

    Footer(){
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(10);

        createAccButton = new Button("Create Account");
        createAccButton.setStyle(defaultButtonStyle);

        this.getChildren().addAll(createAccButton);
        this.setAlignment(Pos.CENTER);
    }

    public Button getCreateAccButton() {
        return createAccButton;
    }
}

class Header extends HBox {

    Header() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setPadding(new Insets(100,0,0,0));
        Text titleText = new Text("PantryPal 2"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 45;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

class CreateScreen extends BorderPane {
    //error messages
    private static final String RESET = "";
    private static final String PASS_ERROR = "Passwords do not match";
    private static final String USER_ERROR = "Username already exists";
    private static final String EMPTY_FIELD_ERROR = "Please enter the missing information";
    private static final String URL = "mongodb+srv://zpliang:LoveMinatoAqua12315@violentevergarden.vm9uhtb.mongodb.net/?retryWrites=true&w=majority";

    private CreateBox cBox;
    private Footer footer;
    private Header header;
    private MongoDB_Account mongodb;

    //private JSONObject acc;

    private String user = "";
    private String pass = "";
    private String accounts = "";

    private Button checkValidAccButton;
    private Button createAccButton;

    CreateScreen(){
        cBox = new CreateBox();
        footer = new Footer();
        header = new Header();
        mongodb = new MongoDB_Account(URL);

        //acc = new JSONObject();

        this.setTop(header);
        this.setCenter(cBox);
        this.setBottom(footer);

        createAccButton = footer.getCreateAccButton();

        addListeners();
    }

    public void addListeners(){
        // try {
        //     if (!Files.exists(Paths.get("accounts.json"))) {
        //         //if file doesn't exist, create file
        //         Files.createFile(Paths.get("accounts.json"));
        //         accounts = "[]";
        //     } else {
        //         accounts = new String(Files.readAllBytes(Paths.get("accounts.json")));
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        /*
         *  check password and username before create account
         *  write the infomation in a json file
         *  
         */
        createAccButton.setOnAction(e -> {
            user = cBox.userInfo();
            pass = cBox.passInfo();
            cBox.checkPass();

            //if any field is missing, display error
            if(user.isEmpty() || pass.isEmpty()){
                cBox.setErrorMsg(EMPTY_FIELD_ERROR);
            }
            else if(!cBox.passMatch()){ //passwords don't match error
                cBox.setErrorMsg(PASS_ERROR);
            }
            else if(!mongodb.checkUsername(user)){ //duplicate username error
                cBox.setErrorMsg(USER_ERROR);
            }
            else{
                //if all requirements are met, allow create account
                //write account info to JSON file
                cBox.setErrorMsg(RESET);
                mongodb.Create(user, pass);
                // acc = AccJSON.intoJSON(user, pass);
                // AccJSON.writeToJSON(accounts, acc);
            }
        });  
    }
}

public class CreateAcc extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        CreateScreen root = new CreateScreen();

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Create Account");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
