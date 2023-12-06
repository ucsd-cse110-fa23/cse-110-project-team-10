package cse110_project;

import org.junit.jupiter.api.Test;

import cse110_project.MongoDB_Test;
import cse110_project.Modify;
import cse110_project.Recipe;
import cse110_project.RecipeStateManager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;

public class RecipeWebTest {
    @Test
    void testAllRepresented() {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        recipes.add(new Recipe("wagner", "dex", RecipeKind.breakfast, "testing"));
        RecipeStateManager manag = new RecipeStateManager(recipes);
        RecipeQuery query = new RecipeQuery("user", "wagner");
        String html = query.renderHtml(manag);
        assertTrue(html.contains("testing"));
        assertTrue(html.contains("img"));
        assertTrue(html.contains("wagner"));
        assertTrue(html.contains("dex"));
    }
}
