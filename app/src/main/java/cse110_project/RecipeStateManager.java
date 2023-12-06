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

    public Recipe getRecipeByName(String name){
        for(Recipe r : recipes){
            if(r.getRecipeName().equals(name)){
                return r;
            }
        }
        return null;
    }
}
