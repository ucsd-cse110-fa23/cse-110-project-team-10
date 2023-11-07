package main;

import java.io.IOException;
import java.net.URISyntaxException;

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

public class postRecipeCreate extends VBox {

    public String rName; 
    public String rDesc;

    private Button saveRecipeButton; 
    private Button editRecipeButton;
    private Button backButton; 
    private Label recipeDescription;
    private Stage postCreateStage;
    private Scene scene;

    // display the generated recipe description in a new popout window
    // pmt = passed meal type, pml = passed meal ingredient list
    public postRecipeCreate(String pmt, String pml) {
        recipeGenerate rg = new recipeGenerate(pmt, pml);
        String ro = "";
        try {
            ro = rg.generate();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
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
}