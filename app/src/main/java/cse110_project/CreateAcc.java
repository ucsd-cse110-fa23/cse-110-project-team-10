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
import javafx.scene.text.TextAlignment;
import javafx.geometry.Insets;
import javafx.scene.text.*;

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

    public String getUserInfo(){
        return userInfo.getText();
    }
}

class CreateBox extends VBox {
    private Info username;
    private Info password;
    private Info confirmPass;

    private boolean isMatch;

    CreateBox(){
        this.setSpacing(5);

        username = new Info("Username: ");
        password = new Info("Password: ");
        confirmPass = new Info("Confirm Password: ");

        this.getChildren().addAll(username, password, confirmPass);
        this.setAlignment(Pos.CENTER);
    }

    public void checkPass(){
        if(password.getUserInfo() == confirmPass.getUserInfo()){
            isMatch = true;
        }
        else{
            isMatch = false;
        }
    }

    public Info userInfo(){
        return username;
    }

    public Info passInfo(){
        return password;
    }
}

class Footer extends HBox {

    private Button checkValidAccButton;
    private Button createAccButton;

    Footer(){
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(10);

        checkValidAccButton = new Button("Check Valid");
        checkValidAccButton.setStyle(defaultButtonStyle);

        createAccButton = new Button("Create Account");
        createAccButton.setStyle(defaultButtonStyle);

        this.getChildren().addAll(checkValidAccButton, createAccButton);
        this.setAlignment(Pos.CENTER);
    }

    public Button getCheckValidAccButton() {
        return checkValidAccButton;
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
    private CreateBox cBox;
    private Footer footer;
    private Header header;

    private Button checkValidAccButton;
    private Button createAccButton;

    CreateScreen(){
        cBox = new CreateBox();
        footer = new Footer();
        header = new Header();

        this.setTop(header);
        this.setCenter(cBox);
        this.setBottom(footer);

        checkValidAccButton = footer.getCheckValidAccButton();
        createAccButton = footer.getCreateAccButton();

        addListeners();
    }

    public void addListeners(){

        /*
         *  set to taggled
         *  write the infomation in a json file
         *  
         */
        createAccButton.setOnAction(e -> {
            //TODO
        });

        /*
         * Check two things:
         *  Confirm pass is the same as pass
         *  Username is not duplicate
         */
        checkValidAccButton.setOnAction(e -> {
            //TODO
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
