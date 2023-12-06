package cse110_project;

import org.junit.jupiter.api.Test;

import cse110_project.Recipe;
import cse110_project.RecipeStateManager;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;

import org.junit.jupiter.api.BeforeEach;

public class MealTagTest {
    Recipe b;
    Recipe l;
    Recipe d;

    @BeforeEach
    public void setup() {
        b = new Recipe("b", "b", RecipeKind.valueOf("breakfast"), "");
        l = new Recipe("l", "l", RecipeKind.valueOf("lunch"), "");
        d = new Recipe("d", "d", RecipeKind.valueOf("dinner"), "");
    }   

    /*
     * Test breakfast meal tag
     */
    @Test
    public void breakfastTagTest(){
        assertEquals("breakfast", b.getRecipeKind().name());
    }

    /*
     * Test lunch meal tag
     */
    @Test
    public void lunchTagTest(){
        assertEquals("lunch", l.getRecipeKind().name());
    }

    /*
     * Test dinner meal tag
     */
    @Test
    public void dinnerTagTest(){
        assertEquals("dinner", d.getRecipeKind().name());
    }
}
