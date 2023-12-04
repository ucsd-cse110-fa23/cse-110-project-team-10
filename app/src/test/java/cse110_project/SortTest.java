package cse110_project;

import org.junit.jupiter.api.Test;

import cse110_project.Recipe;
import cse110_project.RecipeStateManager;
import cse110_project.Sort;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;

public class SortTest {
    RecipeStateManager testList;
    ArrayList<Recipe> sortList;
    Sort testSort;
    RecipeKind test = RecipeKind.valueOf("breakfast");

    @BeforeEach
    public void setup() {
        testList = new RecipeStateManager();
        Recipe a = new Recipe("a", "a", test);
        Recipe b = new Recipe("b", "b", test);
        Recipe c = new Recipe("c", "c", test);
        Recipe d = new Recipe("d", "d", test);

        testList.addRecipe(c);
        testList.addRecipe(a);
        testList.addRecipe(d);
        testList.addRecipe(b);

        testSort = new Sort();
    }

    /**
     * Test sorting default setting, most recent to least recent
     */
    @Test
    public void sortDefaultTest() throws Exception {
        sortList = testSort.sortDefault(testList.getRecipes());

        assertEquals(testList.getRecipes(), sortList);
    }
    
    /**
     * Test sorting least recent to most recent
     */
    @Test
    public void sortLeastRecentTest() throws Exception {
        sortList = testSort.sortOldest(testList.getRecipes());

        assertEquals("b", sortList.get(0).getRecipeName());
        assertEquals("c", sortList.get(3).getRecipeName());
    }

    /**
     * Test sorting by alphabetical order
     */
    @Test
    public void sortAlphabetOrder() throws Exception {
        sortList = testSort.sortAlphabetOrder(testList.getRecipes());

        assertEquals("a", sortList.get(0).getRecipeName());
        assertEquals("b", sortList.get(1).getRecipeName());
        assertEquals("c", sortList.get(2).getRecipeName());
        assertEquals("d", sortList.get(3).getRecipeName());
    }

    /**
     * Test sorting by reverse alphabetical order
     */
    @Test
    public void sortReverseAlphabetOrder() throws Exception {
        sortList = testSort.sortReverseAlphabet(testList.getRecipes());

        assertEquals("d", sortList.get(0).getRecipeName());
        assertEquals("a", sortList.get(3).getRecipeName());
    }
}
