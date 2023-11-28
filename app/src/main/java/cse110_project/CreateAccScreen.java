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

import cse110_project.LoginScreen;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.*;
import java.net.*;
import java.util.*;

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
    private Button backButton;

    Footer(){
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        this.setPrefSize(500, 60);
        this.setPadding(new Insets(0,0,100,0));
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(10);

        createAccButton = new Button("Sign Up");
        createAccButton.setStyle(defaultButtonStyle);
        backButton = new Button("Sign In");

        this.getChildren().addAll(createAccButton, backButton);
        this.setAlignment(Pos.CENTER);
    }

    public Button getCreateAccButton() {
        return createAccButton;
    }

    public Button getBackButton(){
        return backButton;
    }
}

public class CreateAccScreen extends BorderPane {
    //error messages
    private static final String RESET = "";
    private static final String PASS_ERROR = "Passwords do not match";
    private static final String USER_ERROR = "Username already exists";
    private static final String EMPTY_FIELD_ERROR = "Please enter the missing information";
    private static final String URL = "mongodb+srv://zpliang:LoveMinatoAqua12315@violentevergarden.vm9uhtb.mongodb.net/?retryWrites=true&w=majority";

    private CreateBox cBox;
    private Footer footer;
    private MongoDB_Account mongodb;
    private LoginScreen login;

    private String user = "";
    private String pass = "";
    private String accounts = "";

    private Button createAccButton;
    private Button backButton;

    public CreateAccScreen(LoginScreen LoginScreen){
        login = LoginScreen;
        cBox = new CreateBox();
        footer = new Footer();
        mongodb = new MongoDB_Account(URL);

        this.setCenter(cBox);
        this.setBottom(footer);

        createAccButton = footer.getCreateAccButton();
        backButton = footer.getBackButton();

        addListeners();
    }

    public Footer getFooter(){
        return footer;
    }

    public CreateBox getCreateBox(){
        return cBox;
    }

    public void addListeners(){
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
                mongodb.CreateAccount(user, pass);
            }
        });
        
        backButton.setOnAction(e -> {
            login.setCenter(login.getLoginBox());
            login.setBottom(login.getFooter());
        });
    }
}



// public class CreateAcc extends Application {
//     public static void main(String[] args) {
//         launch(args);
//     }

//     @Override
//     public void start(Stage primaryStage) {

//         CreateScreen root = new CreateScreen();

//         Scene scene = new Scene(root, 800, 600);

//         primaryStage.setTitle("Create Account");
//         primaryStage.setScene(scene);
//         primaryStage.setResizable(false);
//         primaryStage.show();
//     }
// }
