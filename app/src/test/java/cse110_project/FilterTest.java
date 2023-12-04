package cse110_project;

import org.junit.jupiter.api.Test;

import cse110_project.Recipe;
import cse110_project.RecipeStateManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class FilterTest {
    RecipeStateManager testList;
    RecipeStateManager filterList;
    Filter testFilter;

    @BeforeEach
    public void setup() {
        testList = new RecipeStateManager();
        filterList = new RecipeStateManager();
        Recipe b1 = new Recipe("b1", "b1", RecipeKind.valueOf("breakfast"));
        Recipe b2 = new Recipe("b2", "b2", RecipeKind.valueOf("breakfast"));
        Recipe l = new Recipe("l", "l", RecipeKind.valueOf("lunch"));
        Recipe d = new Recipe("d", "d", RecipeKind.valueOf("dinner"));

        testList.addRecipe(b2);
        testList.addRecipe(d);
        testList.addRecipe(l);
        testList.addRecipe(b1);

        testFilter = new Filter(testList);
    }

    /**
     * Test filtering default meal type
     */
    @Test
    public void filterDefaultTest() throws Exception {
        filterList = testFilter.filterType("default");

        assertEquals(testList.getRecipes(), filterList.getRecipes());
    }
    
    /**
     * Test filtering breakfast meal type
     */
    @Test
    public void filterBreakfastTest() throws Exception {
        filterList = testFilter.filterType("breakfast");

        for(Recipe r : filterList.getRecipes()){
            assertEquals(RecipeKind.valueOf("breakfast"), r.getRecipeKind());
        }
        assertEquals(2, filterList.getRecipes().size());
        assertEquals("b2", filterList.getRecipes().get(0).getRecipeName());
        assertEquals("b1", filterList.getRecipes().get(1).getRecipeName());
    }

    /**
     * Test filtering lunch meal type
     */
    @Test
    public void filterLunchTest() throws Exception {
        filterList = testFilter.filterType("lunch");

        for(Recipe r : filterList.getRecipes()){
            assertEquals(RecipeKind.valueOf("lunch"), r.getRecipeKind());
        }
        assertEquals(1, filterList.getRecipes().size());
        assertEquals("l", filterList.getRecipes().get(0).getRecipeName());
    }

    /**
     * Test filtering dinner meal type
     */
    @Test
    public void filterDinnerTest() throws Exception {
        filterList = testFilter.filterType("dinner");

        for(Recipe r : filterList.getRecipes()){
            assertEquals(RecipeKind.valueOf("dinner"), r.getRecipeKind());
        }
        assertEquals(1, filterList.getRecipes().size());
        assertEquals("d", filterList.getRecipes().get(0).getRecipeName());
    }
}
