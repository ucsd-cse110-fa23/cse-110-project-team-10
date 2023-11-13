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
    RecipeStateManager testRecipeList;
    String recipeName;
    String recipeDetails;
    RecipeKind mealType;

    @BeforeEach
    public void setup() {
        testRecipeList = new RecipeStateManager();
        for(int i = 0; i < 3; i++){
            recipeName = "Recipe: " + i;
            recipeDetails = "";
            mealType = RecipeKind.valueOf("lunch");
            Recipe recipe = new Recipe(recipeName, recipeDetails, mealType);
            testRecipeList.addRecipe(recipe);
        }
    }

    /**
     * Test the positions of the previously added recipes
     */
    @Test
    public void testRecipesOrder() throws Exception {
        assertEquals("Recipe: 2", testRecipeList.getRecipes().get(2).getRecipeName());
        assertEquals("Recipe: 0", testRecipeList.getRecipes().get(0).getRecipeName());
    }

    /**
     * Test the positions of recipe after delete
     */
    @Test
    public void testOrderAfterDelete() throws Exception {
        testRecipeList.deleteRecipe(testRecipeList.getRecipes().get(1));
        assertEquals("Recipe: 2", testRecipeList.getRecipes().get(1).getRecipeName());
    }

    /**
     * Test the position of the newly added recipe
     */
    @Test
    public void testOrderAfterAdd() throws Exception {
        Recipe newRecipe = new Recipe("new name", "new details", RecipeKind.valueOf("breakfast"));
        testRecipeList.addRecipe(newRecipe);
        assertEquals(newRecipe, testRecipeList.getRecipes().get(3));
        assertEquals("Recipe: 1", testRecipeList.getRecipes().get(1).getRecipeName());
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
        Recipe newRecipe = new Recipe("name", result, RecipeKind.valueOf("dinner"));
        testRecipeList.addRecipe(newRecipe);

        assertEquals("name", testRecipeList.getRecipes().get(3).getRecipeName());
        assertEquals(result, testRecipeList.getRecipes().get(3).getRecipeDescription());

        assertEquals(RecipeKind.valueOf("lunch"), testRecipeList.getRecipes().get(1).getRecipeKind());
    }
}