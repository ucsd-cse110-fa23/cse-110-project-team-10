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

public class NewRecipeTest {
    mockWhisper wapi;
    private mockRecipeGenerate rgt;
    private String mType;
    private String mIngr;
    private String rgtout;

    @BeforeEach 
    void setUp() {
        mType = "lunch";
        mIngr = "cheese,onions,turkey,bread,mustard";
        wapi = new mockWhisper();
        String voicemType = wapi.mockHandleVoiceInput(mType);
        String voicemIngr = wapi.mockHandleVoiceInput(mIngr);
        rgt = new mockRecipeGenerate(voicemType, voicemIngr);
        rgtout = rgt.generate();
        rgtout = rgtout.toLowerCase();
    }

    // Test recipe creation from voice input 
    @Test
    void testNewRecipeCreation() {
        boolean correctMealType = false;
        boolean correctMealDescription = false;
        if (rgtout.contains(mType)) {
            correctMealType = true;
        }
        
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

        if (percentiused >= acceptable) {
            correctMealDescription = true;
        }

        assertTrue(correctMealType && correctMealDescription);
    } 
}

