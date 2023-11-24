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
    }

    public void checkPass(){
        if(password.getUserInfo() == confirmPass.getUserInfo()){
            isMatch = true;
        }
        else{
            isMatch = false;
        }
    }
}

class CreateScreen extends BorderPane {
    private CreateBox cBox;
    private Button createButton;

    CreateScreen(){
        cBox = new CreateBox();

        this.setCenter(cBox);

    }
}

public class CreateAcc extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        CreateScreen root = new CreateScreen();

        Scene scene = new Scene(root, 1280, 720);

        primaryStage.setTitle("Create Account");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
