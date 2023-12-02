package cse110_project;

import java.util.ArrayList;

import cse110_project.Recipe;
import cse110_project.RecipeStateManager;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Filter {
    private RecipeStateManager recipeList;

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
}
