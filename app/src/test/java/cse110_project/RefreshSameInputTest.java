package cse110_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cse110_project.Recipe;

import java.beans.Transient;
import java.io.IOException;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RefreshSameInputTest{
    String oldingredients = "bread, lettuce, ham, cheese, tomato";
    String newIngredients = "bread, lettuce, ham, cheese, tomato";
    Recipe oldRecipe = new Recipe("Sandwich", "abcdef", RecipeKind.valueOf("lunch"), "https://A");
    Recipe newRecipe = new Recipe("Burrito", "ghijkl", RecipeKind.valueOf("lunch"), "https://B");

    @BeforeEach
    public void setup(){
        
    }

    @Test
    void sameIngredient(){
        assertEquals(oldingredients, newIngredients);
    }

    @Test
    void differentRecipe(){
        assertFalse(oldRecipe.getRecipeDescription().equals(newRecipe.getRecipeDescription()));
    }
}