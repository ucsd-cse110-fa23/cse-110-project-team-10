package cse110_project;

import cse110_project.recipeGenerate;
import java.beans.Transient;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class saveAndEditRecipeTest {
    private Recipe test;
    private String name;
    private String ingred;
    private String newName;
    private String newIngred;

    @BeforeEach 
    void setUp() {
        /* 
        test = new Recipe();
        name = "sandwich";
        ingred = "apple, banana, cabbage, dikon, eggplant, flour";
        test.name = name;
        test.description = ingred;
        */
    }
    @Test 
    void testSaveRecipeButton(){
        /* 
        String newIngred = "apple, banana, cabbage, dikon, eggplant, flour, grape, horseradish, ice";
        ingred = newIngred;
        assertEquals(ingred, newIngred);
        */
    }

    @Test
    void testBackButton(){

    }

    @Test 
    void testEditButton(){

    }

    @Test
    void testViewRecipeDetails(){

    }
}
