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

import cse110_project.CreateAccScreen;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.*;
import java.net.*;
import java.util.*;

//import org.w3c.dom.Text;

class LoginFooter extends HBox {

    private Button createAccButton;
    private Button loginButton;

    LoginFooter(){
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        this.setPrefSize(500, 60);
        this.setPadding(new Insets(0,0,100,0));
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(10);

        createAccButton = new Button("Create Account");
        createAccButton.setStyle(defaultButtonStyle);
        loginButton = new Button("Login");
        loginButton.setStyle(defaultButtonStyle);

        this.getChildren().addAll(createAccButton, loginButton);
        this.setAlignment(Pos.CENTER);
    }

    public Button getCreateAccButton() {
        return createAccButton;
    }

    public Button getLoginButton() {
        return loginButton;
    }
}

class LoginHeader extends HBox {

    LoginHeader() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setPadding(new Insets(100,0,0,0));
        Text titleText = new Text("PantryPal 2"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 45;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

class LoginInfo extends HBox{
    private Label infoLabel;
    private TextField userInfo;

    LoginInfo(String label){
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

class LoginBox extends VBox {
    private LoginInfo username;
    private LoginInfo password;

    private Label errorMessage;
    private boolean isMatch;

    LoginBox(){
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(5);

        username = new LoginInfo("Username: ");
        password = new LoginInfo("Password: ");

        errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red");

        isMatch = false;

        this.getChildren().addAll(username, password, errorMessage);
        this.setAlignment(Pos.CENTER);
    }

    public boolean passwordMatch(){
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

public class LoginScreen extends BorderPane {
    private static final String EMPTY_FIELD_ERROR = "Please enter the missing information";
    private static final String ACC_ERROR = "Invalid Username or Password";
    private static final String URL = "mongodb+srv://zpliang:LoveMinatoAqua12315@violentevergarden.vm9uhtb.mongodb.net/?retryWrites=true&w=majority";

    private String user = "";
    private String pass = "";

    private CreateAccScreen createscreen;
    private LoginBox lbox;
    private LoginHeader header;
    private LoginFooter footer;
    private App app;

    private MongoDB_Account mongodb;

    private Button createAccButton;
    private Button loginButton;

    //view
    public LoginScreen(App app){
        lbox = new LoginBox();
        header = new LoginHeader();
        footer = new LoginFooter();
        mongodb = new MongoDB_Account(URL);
        this.app = app;


        createAccButton = footer.getCreateAccButton();
        loginButton = footer.getLoginButton();

        this.setCenter(lbox);
        this.setBottom(footer);
        this.setTop(header);

        addListeners();
    }

    public LoginFooter getFooter(){
        return footer;
    }

    public LoginBox getLoginBox(){
        return lbox;
    }

    //controller
    public void addListeners(){
        createAccButton.setOnAction(e -> {
            createscreen = new CreateAccScreen(this);
            this.setCenter(createscreen.getCreateBox());
            this.setBottom(createscreen.getFooter());
        });

        loginButton.setOnAction(e -> {
            user = lbox.userInfo();
            pass = lbox.passInfo();

            if(user.isEmpty() || pass.isEmpty()){
                lbox.setErrorMsg(EMPTY_FIELD_ERROR);
            }
            else if(!mongodb.LookUpAccount(user, pass)){ //check password
                lbox.setErrorMsg(ACC_ERROR);
            }else{
                app.transitionToMainScreen();
            }
        });
    }
}