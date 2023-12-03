package cse110_project;

import org.junit.jupiter.api.Test;

import cse110_project.Recipe;
import cse110_project.RecipeStateManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class SortTest {
    RecipeStateManager testList;
    RecipeStateManager sortList;
    Sort testSort;
    RecipeKind test = RecipeKind.valueOf("breakfast");

    @BeforeEach
    public void setup() {
        testList = new RecipeStateManager();
        sortList = new RecipeStateManager();
        Recipe a = new Recipe("a", "a", test);
        Recipe b = new Recipe("b", "b", test);
        Recipe c = new Recipe("c", "c", test);
        Recipe d = new Recipe("d", "d", test);

        testList.addRecipe(c);
        testList.addRecipe(a);
        testList.addRecipe(d);
        testList.addRecipe(b);

        testSort = new Sort(testList);
    }

    /**
     * Test sorting default setting, most recent to least recent
     */
    @Test
    public void sortDefaultTest() throws Exception {
        sortList = testSort.sort("default: most recent");

        assertEquals(testList.getRecipes(), sortList.getRecipes());
    }
    
    /**
     * Test sorting least recent to most recent
     */
    @Test
    public void sortLeastRecentTest() throws Exception {
        sortList = testSort.sort("least recent");

        assertEquals("b", sortList.getRecipes().get(0).getRecipeName());
        assertEquals("c", sortList.getRecipes().get(3).getRecipeName());
    }

    /**
     * Test sorting by alphabetical order
     */
    @Test
    public void sortAlphabetOrder() throws Exception {
        sortList = testSort.sort("a-z");

        assertEquals("a", sortList.getRecipes().get(0).getRecipeName());
        assertEquals("b", sortList.getRecipes().get(1).getRecipeName());
        assertEquals("c", sortList.getRecipes().get(2).getRecipeName());
        assertEquals("d", sortList.getRecipes().get(3).getRecipeName());
    }

    /**
     * Test sorting by reverse alphabetical order
     */
    @Test
    public void sortReverseAlphabetOrder() throws Exception {
        sortList = testSort.sort("z-a");

        assertEquals("d", sortList.getRecipes().get(0).getRecipeName());
        assertEquals("a", sortList.getRecipes().get(3).getRecipeName());
    }
}
