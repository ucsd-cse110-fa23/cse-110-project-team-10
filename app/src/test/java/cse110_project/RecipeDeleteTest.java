package cse110_project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.ArrayList;

public class RecipeDeleteTest {
    
    @Test
    void testDeletion(){
        RecipeStateManager test1 = new RecipeStateManager();

        Recipe r1 = new Recipe();
        Recipe r2 = new Recipe();
        Recipe r3 = new Recipe();


        test1.addRecipe(r1);
        test1.addRecipe(r2);
        test1.addRecipe(r3);
        test1.deleteRecipe(r3);


        for(Recipe r : test1.getRecipes()){
            assertFalse(r == r3);
        }
        
    }

}
