package cse110_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import cse110_project.JSONOperations;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
//import main.java.cse110_project.Recipe;.
//import main.java.cse110_project.Recipe;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SaveAndReadJSONtest {
    RecipeStateManager testState = new RecipeStateManager();
    String rName;
    String rDesc;
    RecipeKind rKind;
    String url = "https://static.toiimg.com/thumb/83740315.cms?width=1200&height=900";

    @BeforeEach
    public void setup() {
        for (int i = 0; i < 5; i++) {
            rName = "Recipe #" + i;
            rDesc = "";
            rKind = RecipeKind.valueOf("dinner");
            Recipe toAdd = new Recipe(rName, rDesc, rKind, url);
            testState.addRecipe(toAdd);
        }

        JSONObject cr;
        try (FileWriter fw = new FileWriter("recipestest.json")) {
            for (Recipe r : testState.getRecipes()) {
                cr = new JSONObject();
                cr.put("name", r.getRecipeName());
                cr.put("description", r.getRecipeDescription());
                cr.put("kind", r.getRecipeKind().name());
                fw.write(cr.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSaveToJSON() {
        String asJSON = JSONOperations.intoJSONString(testState);
        RecipeStateManager parsedFromJSON = JSONOperations.fromJSONString(asJSON);

        for(int i = 0; i < testState.getRecipes().size(); i++) {
            Recipe rFrom = testState.getRecipes().get(i);
            Recipe rParsed = parsedFromJSON.getRecipes().get(i);
            assertEquals(rFrom.getRecipeKind(), rParsed.getRecipeKind());
            assertEquals(rFrom.getRecipeName(), rParsed.getRecipeName());
            assertEquals(rFrom.getRecipeDescription(), rParsed.getRecipeDescription());
        }
    }

    @Test
    public void testReadFromJSON() {
        JSONObject cr2;
        String returnString = "A";
        String returnString2 = "B";
        try (FileWriter fw = new FileWriter("recipes.json")) {
            for (Recipe r : testState.getRecipes()) {
                cr2 = new JSONObject();
                cr2.put("name", r.getRecipeName());
                cr2.put("description", r.getRecipeDescription());
                cr2.put("kind", r.getRecipeKind().name());
                fw.write(cr2.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            returnString = new String(Files.readAllBytes(Paths.get("recipes.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            returnString2 = new String(Files.readAllBytes(Paths.get("recipestest.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(returnString, returnString2);
    }
}
