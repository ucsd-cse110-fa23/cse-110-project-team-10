package cse110_project;

import org.junit.jupiter.api.Test;

import cse110_project.MongoDB_Test;
import cse110_project.Modify;
import cse110_project.Recipe;
import cse110_project.RecipeStateManager;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.*;
import org.json.*;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;

import org.junit.jupiter.api.BeforeEach;

public class E2ETest {
    String testUser;
    String testPass;
    String URL = "mongodb+srv://zpliang:LoveMinatoAqua12315@violentevergarden.vm9uhtb.mongodb.net/?retryWrites=true&w=majority";
    String sandwichURL = "https://static.toiimg.com/thumb/83740315.cms?width=1200&height=900";
    String noodleURL = "https://www.chilipeppermadness.com/wp-content/uploads/2023/06/Gochujang-Noodles-Recipe-SQ.jpg";
    MongoDB_Test testMongodb;
    mockRecipeGenerate mockRecipe1;
    mockRecipeGenerate mockRecipe2;
    mockImageGenerate mockImage;

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
        mockImage = new mockImageGenerate();
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
     * Integration test for image preview and refresh for new recipe
     */
    @Test
    public void imageAndRegenerateRecipeTest() throws Exception {
        String type = "type";
        String ingredient = "ingredient";
        mockRecipe1 = new mockRecipeGenerate(type, ingredient);
        String recipe1 = mockRecipe1.generate();

        mockRecipe2 = new mockRecipeGenerate(type, ingredient);
        String recipe2 = mockRecipe2.generate();

        assertTrue(recipe1.contains(type));
        assertTrue(recipe2.contains(type));
        assertTrue(recipe1.contains(ingredient));
        assertTrue(recipe2.contains(ingredient));

        assertFalse(mockRecipe1 == mockRecipe2);

        String imageURL = "image";
        String image = mockImage.mockGenerate(imageURL);

        Recipe r = new Recipe(recipe2, recipe2, RecipeKind.valueOf("breakfast"), image);
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

    /*
     * E2E Scenario Test
     */
    @Test
    public void scenarioTest() {
        //create account
        assertFalse(testMongodb.LookUpAccount(testUser, testPass));
        testMongodb.CreateAccount(testUser, testPass);

        //login
        assertTrue(testMongodb.LookUpAccount(testUser, testPass));

        //store recipe to account
        Document recipe = new Document("$set", Document.parse(JSONOperations.intoJSONString(testList)));
        testMongodb.updateRecipetoAccount(testUser, recipe);

        //get recipe from corresponding account
        resultList = testMongodb.grabRecipeFromAccount(testUser);
        for(int i = 0; i < resultList.getRecipes().size(); i++){
            assertEquals(testList.getRecipes().get(i).getRecipeName(), resultList.getRecipes().get(i).getRecipeName());
        }

        //filter and sort list
        Modify mTest = new Modify(resultList);
        resultList = mTest.modify("breakfast", "z-a");
        for(Recipe r : resultList.getRecipes()){
            assertEquals(RecipeKind.valueOf("breakfast"), r.getRecipeKind());
        }
        assertEquals("b2", resultList.getRecipes().get(0).getRecipeName());
        assertEquals("b1", resultList.getRecipes().get(1).getRecipeName());

        //test regenerate
        String type = "type";
        String ingredient = "ingredient";
        mockRecipe1 = new mockRecipeGenerate(type, ingredient);
        String recipe1 = mockRecipe1.generate();

        mockRecipe2 = new mockRecipeGenerate(type, ingredient);
        String recipe2 = mockRecipe2.generate();

        assertTrue(recipe1.contains(type));
        assertTrue(recipe2.contains(type));
        assertTrue(recipe1.contains(ingredient));
        assertTrue(recipe2.contains(ingredient));

        assertFalse(mockRecipe1 == mockRecipe2);

        //test image preview
        String imageURL = "image";
        String image = mockImage.mockGenerate(imageURL);

        Recipe r = new Recipe(recipe2, recipe2, RecipeKind.valueOf("breakfast"), image);

        resultList.addRecipe(r);

        assertEquals(recipe2, resultList.getRecipes().get(resultList.getRecipes().size()-1).getRecipeName());
        assertEquals(image, resultList.getRecipes().get(resultList.getRecipes().size()-1).getRecipeImage());
    }
}
