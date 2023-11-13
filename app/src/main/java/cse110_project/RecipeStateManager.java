package cse110_project;

import java.util.ArrayList;

public class RecipeStateManager {
    private ArrayList<Recipe> recipes;

    RecipeStateManager(){
        recipes = new ArrayList<Recipe>();
    }

    RecipeStateManager(ArrayList<Recipe> recipes){
        this.recipes = recipes;
    }


    public void addRecipe(Recipe r){
        recipes.add(r);
    }  

    public void deleteRecipe(Recipe r){
        recipes.remove(r);
    }

    public ArrayList<Recipe> getRecipes(){
        return recipes;
    }
}
