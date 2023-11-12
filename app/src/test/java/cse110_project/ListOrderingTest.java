package cse110_project;

import org.junit.jupiter.api.Test;

import cse110_project.Recipe;
import cse110_project.Whisper;
import cse110_project.mockWhisper;
import cse110_project.mockRecipeGenerate;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;

public class ListOrderingTest {
    ArrayList<Recipe> testRecipeList;
    String recipeName;
    String recipeDetails;
    RecipeKind mealType;

    @BeforeEach
    public void setup() {
        testRecipeList = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            recipeName = "Recipe: " + i;
            recipeDetails = "";
            mealType = RecipeKind.valueOf("Lunch");
            Recipe recipe = new Recipe(recipeName, recipeDetails, mealType);
            testRecipeList.add(0, recipe);
        }
    }

    /**
     * Test the positions of the previously added recipes
     */
    @Test
    public void testRecipesOrder() throws Exception {
        assertEquals("Recipe: 0", testRecipeList.get(2).getRecipeName());
        assertEquals("Recipe: 2", testRecipeList.get(0).getRecipeName());
    }

    /**
     * Test the position of the newly added recipe
     */
    @Test
    public void testNewRecipeOrder() throws Exception {
        Recipe newRecipe = new Recipe("new name", "new details", RecipeKind.valueOf("Breakfast"));
        testRecipeList.add(0,newRecipe);
        assertEquals(newRecipe, testRecipeList.get(0));
        assertEquals("Recipe: 2", testRecipeList.get(1).getRecipeName());
    }

    /**
     * Test order of list with create recipe feature
     */
    @Test
    public void createRecipeOrder() throws Exception {
        File audio = new File("recording.wav");
        WhisperAPI whisper = new mockWhisper();
        String mType = whisper.handleVoiceInput(audio);
        String ingredient = whisper.handleVoiceInput(audio);
        mockRecipeGenerate gpt = new mockRecipeGenerate(mType, ingredient);

        String result = gpt.generate();
        Recipe newRecipe = new Recipe("name", result, RecipeKind.valueOf("Dinner"));
        testRecipeList.add(0,newRecipe);

        assertEquals("name", testRecipeList.get(0).getRecipeName());
        assertEquals(result, testRecipeList.get(0).getRecipeDescription());

        assertEquals(RecipeKind.valueOf("Lunch"), testRecipeList.get(2).getRecipeKind());
    }
}