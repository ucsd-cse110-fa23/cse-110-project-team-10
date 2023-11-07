package test.main;

import main.recipeGenerate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class recipeGenerateTest {
    private recipeGenerate rgt;
    private String mType;
    private String mIngr;
    private String rgtout;

    @BeforeEach 
    void setUp() {
        mType = "lunch";
        mIngr = "cheese,onions,turkey,bread,mustard";
        rgt = new recipeGenerate(mType, mIngr);
        try {
            rgtout = rgt.generate();
            rgtout = rgtout.toLowerCase();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testNewRecipeButton() {
        assertTrue(true);
    }

    // Test that generated meal is of the specified meal type
    @Test
    void testMealType() {
        assertTrue(rgtout.contains(mType));
    }

    // Test that generated recipe contains specified ingredients
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
        /* 
        System.out.println("icount: " + (float)(icount));
        System.out.println("list length: " + (float)(foodList.length));
        System.out.println(percentiused);
        System.out.println(Arrays.toString(foodList));
        System.out.println(rgtout);
        */
        assertTrue(percentiused >= acceptable);
    }
}
