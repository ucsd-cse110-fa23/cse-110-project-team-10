import java.util.ArrayList;

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

        // top titlebar
        {
            HBox titleHbox = new HBox();
            titleHbox.setAlignment(Pos.CENTER_RIGHT);
            Button newRecipe = new Button("New Recipe");
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