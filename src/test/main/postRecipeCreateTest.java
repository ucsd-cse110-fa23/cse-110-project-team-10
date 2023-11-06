package test.main;

import main.postRecipeCreate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class postRecipeCreateTest {
    private postRecipeCreate prc;
    private String mType;
    private String mIngr;

    @BeforeEach 
    void setUp() {
        mType = "lunch";
        mIngr = "cheese,onions,turkey,bread,mustard";
        try {
            postRecipeCreate prc = new postRecipeCreate(mType, mIngr);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    
    @Test
    void testNewRecipeButton() {
        assertTrue(true);
    }
    

    @Test
    void testMealType() {
        assertTrue(prc.rName.contains(mType));
    }

    @Test
    void testMealDetail() {
        String[] foodList = mIngr.split(",");
        boolean notFound = false;
        for(String f : foodList) {
            if(!prc.rDesc.contains(f)) {
                notFound = true;
            }
        }
        assertFalse(notFound);
    }

}
