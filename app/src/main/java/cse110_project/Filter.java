package cse110_project;

import java.util.ArrayList;

import cse110_project.Recipe;
import cse110_project.RecipeStateManager;
import javafx.scene.control.*;
import javafx.scene.layout.*;

// class FilterBox extends HBox{
//     private ComboBox filterBox; 
//     private Label label;

//     FilterBox(){
//         label = new Label("Filter");

//         filterBox = new ComboBox();
//         filterBox.getItems().addAll(
//             "Default",
//             "Breakfast",
//             "Lunch",
//             "Dinner"
//         );  
//         filterBox.setValue("Default");

//         this.getChildren().addAll(label, filterBox);
//     }

//     public ComboBox getBox(){
//         return filterBox;
//     }
// }

public class Filter {
    private RecipeStateManager recipeList;
    // private FilterBox filterBox = new FilterBox();
    // private ComboBox filterBox; 
    Filter(){}

    Filter(RecipeStateManager inputList){
        recipeList = new RecipeStateManager(inputList.getRecipes());
    }
    
    public RecipeStateManager filterType(String mealType){
        RecipeStateManager newList = new RecipeStateManager();

        if(mealType.equals("default")){
            return recipeList;
        }
        else{
            for(Recipe r: recipeList.getRecipes()){
                if(r.getRecipeKind() == RecipeKind.valueOf(mealType)){
                    newList.addRecipe(r);
                }
            }
        }

        return newList;
    }

    // public ComboBox getFilterBox(){
        
    //     filterBox.getItems().addAll(
    //         "Default",
    //         "Breakfast",
    //         "Lunch",
    //         "Dinner"
    //     );  
    //     filterBox.setValue("Default");
    //     return filterBox;
    // }

    // public FilterBox getFilterBox(){
    //     return filterBox;
    // }
}
