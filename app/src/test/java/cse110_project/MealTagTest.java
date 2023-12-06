package cse110_project;

import org.junit.jupiter.api.Test;

import cse110_project.Recipe;
import cse110_project.RecipeStateManager;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;

import org.junit.jupiter.api.BeforeEach;

public class MealTagTest {

    @Test
    public void mealTagTest() {
        Recipe b = new Recipe("b", "b", RecipeKind.valueOf("breakfast"), "");
        Recipe l = new Recipe("l", "l", RecipeKind.valueOf("lunch"), "");
        Recipe d = new Recipe("d", "d", RecipeKind.valueOf("dinner"), "");

        assertEquals("breakfast", b.getRecipeKind().name());
        assertEquals("lunch", l.getRecipeKind().name());
        assertEquals("dinner", d.getRecipeKind().name());

    }   
}
