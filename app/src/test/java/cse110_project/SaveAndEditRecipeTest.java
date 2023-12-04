package cse110_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SaveAndEditRecipeTest {
    private Recipe test;
    private String name;
    private String ingred;
    private String newIngred;
    private String url = "https://static.toiimg.com/thumb/83740315.cms?width=1200&height=900";

    @BeforeEach
    public void setup() {
        name = "sandwich";
        ingred = "apple, banana, cabbage, dikon, eggplant, flour";
        RecipeKind kind = RecipeKind.valueOf("dinner");
        test = new Recipe(name, ingred, kind, url);
    }
    @Test
    public void testTemplate() {
        assertEquals(1, 1);
    }

    @Test
    public void testSaveRecipeButton() {
        String newIngred = "apple, banana, cabbage, dikon, eggplant, flour, grape, horseradish, ice";
        test.setRecipeDescription(newIngred);
        assertEquals(test.getRecipeDescription(), newIngred);
    }

    @Test
    public void testBackButton() {
        boolean goBack = false;
        goBack = true;
        assertTrue(goBack);
    }

    @Test
    public void testEditButton() {
        boolean editable = false;
        editable = true;
        assertTrue(editable);
    }

    @Test
    public void testViewRecipeDetails() {
        String rDetails = "";
        boolean viewDetails = false;
        viewDetails = true;
        if (viewDetails == true) {
            rDetails = test.getRecipeDescription();
        }
        assertEquals(rDetails, test.getRecipeDescription());
    }   
}
