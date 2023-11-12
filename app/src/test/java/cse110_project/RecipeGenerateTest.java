package cse110_project;

import cse110_project.recipeGenerate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecipeGenerateTest {
    private mockRecipeGenerate rgt;
    private String mType;
    private String mIngr;
    private String rgtout;

    @BeforeEach 
    void setUp() {
        mType = "lunch";
        mIngr = "cheese,onions,turkey,bread,mustard";
        rgt = new mockRecipeGenerate(mType, mIngr);
        rgtout = rgt.generate();
        rgtout = rgtout.toLowerCase();
    }

    // Test that generated meal is of the specified meal type
    @Test
    void testMealType() {
        assertTrue(rgtout.contains(mType));
    }

    // Test that generated recipe contains specified ingredients
    // check that the generated recipe uses at least 80% of the ingredients we specified
    @Test
    void testMealDetail() {
        String[] foodList = mIngr.split(",");
        int icount = 0;
        for(int i = 0; i<foodList.length; i++) {
            String f = foodList[i].toLowerCase();
            if(rgtout.contains(f)) {
                icount++;
            }
        }
        float percentiused = (float)(icount)/(float)(foodList.length);
        float acceptable = (float) 0.8;
        
        assertTrue(percentiused >= acceptable);
    }
}
