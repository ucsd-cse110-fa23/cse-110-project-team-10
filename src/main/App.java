package main;

import java.util.ArrayList;

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

class postRecipeCreate extends VBox {

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

// the names of these enums are shown in UI so should be nice and not programmery. If changed have to update
enum RecipeKind {
    Breakfast,
    Lunch,
    Dinner
}

class Recipe {
    public RecipeKind kind;
    public String name = "";
    public String description = "";
}

// JavaFX Application main entry point
public class App extends Application {

    private postRecipeCreate prc;
    
    public static void main(String[] args) {
        launch(args);
    }

    private ArrayList<Recipe> recipes;
    private VBox recipesUI;

    private void addRecipeUI(Recipe forRecipe) {
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
                Label title = new Label(forRecipe.name);
                title.setAlignment(Pos.CENTER_LEFT);
                BorderPane.setAlignment(title, Pos.CENTER_LEFT);
                descInside.setLeft(title);

                // recipe type
                Label recipeType = new Label(forRecipe.kind.name());
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

            recipesUI.getChildren().add(recipePane);

    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Recipe Run");

        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.TOP_CENTER);

        String din = "dinner";
        String il = "carrots, beef, salt, lettuce, tomatoes";
        
        prc = new postRecipeCreate(din, il);

        // top titlebar
        {
            HBox titleHbox = new HBox();
            titleHbox.setAlignment(Pos.CENTER_RIGHT);
            Button newRecipe = new Button("New Recipe");
            newRecipe.setOnMouseClicked(e -> {
                prc.postRecipeCreateDisplay();
            });
            newRecipe.setMinHeight(50.0);
            Region spacer = new Region();
            spacer.setMinWidth(50.0);
            titleHbox.getChildren().addAll(newRecipe, spacer);
            mainBox.getChildren().add(titleHbox);
        }

        recipesUI = new VBox();
        recipes = new ArrayList<Recipe>();
        recipesUI.setAlignment(Pos.TOP_CENTER);
        for (int i = 0; i < 100; i++) {
            Recipe toAdd = new Recipe();
            toAdd.kind = RecipeKind.values()[i % 3];
            toAdd.name = "Recipe #" + i;
            // for deleting recipes you probably want to store each recipe's UI object in the Recipe object and call delete through there
            recipes.add(toAdd);
            addRecipeUI(toAdd);
        }
        // mainBox.getChildren().add(scrollPaneContents);
        ScrollPane pane = new ScrollPane();
        pane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            recipesUI.setPrefWidth(newValue.getWidth() - 1);
        });
        pane.setContent(recipesUI);
        mainBox.getChildren().add(pane);

        Scene scene = new Scene(mainBox, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}