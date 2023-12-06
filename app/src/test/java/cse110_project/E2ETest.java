package cse110_project;

import org.junit.jupiter.api.Test;

import cse110_project.MongoDB_Test;
import cse110_project.Modify;
import cse110_project.Recipe;
import cse110_project.RecipeStateManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class E2ETest {
    String testUser;
    String testPass;
    String URL = "mongodb+srv://zpliang:LoveMinatoAqua12315@violentevergarden.vm9uhtb.mongodb.net/?retryWrites=true&w=majority";
    MongoDB_Test testMongodb;

    Modify testModify;
    RecipeStateManager testList;
    RecipeStateManager resultList;

    @BeforeEach
    public void setup() {
        testUser = "test";
        testPass = "test";
        testMongodb = new MongoDB_Test(URL);

        //if account exists, delete before testing
        testMongodb.Delete(testUser);

        testList = new RecipeStateManager();
        Recipe b1 = new Recipe("b1", "b1", RecipeKind.valueOf("breakfast"), "");
        Recipe b2 = new Recipe("b2", "b2", RecipeKind.valueOf("breakfast"), "");
        Recipe l = new Recipe("l", "l", RecipeKind.valueOf("lunch"), "");
        Recipe d1 = new Recipe("d1", "d", RecipeKind.valueOf("dinner"), "");
        Recipe d2 = new Recipe("d2", "d", RecipeKind.valueOf("dinner"), "");

        testList.addRecipe(b2);
        testList.addRecipe(d1);
        testList.addRecipe(l);
        testList.addRecipe(b1);
        testList.addRecipe(d2);

        testModify = new Modify(testList);
    }

    /**
     * Integration test for create account and login.
     */
    @Test
    public void createAccAndLoginTest() throws Exception {
        assertFalse(testMongodb.LookUpAccount(testUser, testPass));

        testMongodb.CreateAccount(testUser, testPass);

        assertTrue(testMongodb.LookUpAccount(testUser, testPass));
    }

    /**
     * Integration test for filtering based on meal tag.
     */
    @Test
    public void filterLunchMealTag() throws Exception {
        resultList = testModify.modify("lunch", "default: most recent");
        for(Recipe r : resultList.getRecipes()){
            assertEquals(RecipeKind.valueOf("lunch"), r.getRecipeKind());
        }

        assertEquals(1, resultList.getRecipes().size());
    }

    /**
     * Integration test for filtering breakfast and sorting alphabetically.
     */
    @Test
    public void filterBreakfastAndSortAlphabeticalOrder() throws Exception {
        resultList = testModify.modify("breakfast", "a-z");
        for(Recipe r : resultList.getRecipes()){
            assertEquals(RecipeKind.valueOf("breakfast"), r.getRecipeKind());
        }

        assertEquals("b1", resultList.getRecipes().get(0).getRecipeName());
        assertEquals("b2", resultList.getRecipes().get(1).getRecipeName());
    }

    /**
     * Integration test for filtering dinner and sorting based on least recent.
     */
    @Test
    public void sortOldestAndFilterDinnerTest() throws Exception {
        resultList = testModify.modify("dinner", "oldest");
        for(Recipe r : resultList.getRecipes()){
            assertEquals(RecipeKind.valueOf("dinner"), r.getRecipeKind());
        }

        assertEquals("d2", resultList.getRecipes().get(0).getRecipeName());
        assertEquals("d1", resultList.getRecipes().get(1).getRecipeName());
    }
}
