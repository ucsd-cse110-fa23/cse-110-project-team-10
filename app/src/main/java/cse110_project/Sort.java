package cse110_project;

import cse110_project.Recipe;
import cse110_project.RecipeStateManager;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.*;

public class Sort {
    private final String DEFAULT = "default: most recent";
    private final String OLDEST = "least recent";
    private final String ALPHABET = "a-z";
    private final String REVERSE_ALPHABET = "z-a";
    private RecipeStateManager recipeList;

    Sort(RecipeStateManager inputList){
        recipeList = new RecipeStateManager(inputList.getRecipes());
    }
    
    public RecipeStateManager sort(String category){
        RecipeStateManager newList;

        if(category.equals(DEFAULT)){
            return recipeList;
        }
        else{
            ArrayList<Recipe> sortedList = new ArrayList<>(recipeList.getRecipes());
            if(category.equals(OLDEST)){
                Collections.reverse(sortedList);
            }
            else if(category.equals(ALPHABET)){
                Collections.sort(sortedList, new Comparator<Recipe>() {
                    @Override
                    public int compare(Recipe r1, Recipe r2) {
                        return r1.getRecipeName().toLowerCase().compareTo(r2.getRecipeName().toLowerCase());
                    }
                });
            }
            else{
                Collections.sort(sortedList, new Comparator<Recipe>() {
                    @Override
                    public int compare(Recipe r1, Recipe r2) {
                        return r2.getRecipeName().toLowerCase().compareTo(r1.getRecipeName().toLowerCase());
                    }
                });
            }

            newList = new RecipeStateManager(sortedList);
        }
        
        return newList;
    }
}
