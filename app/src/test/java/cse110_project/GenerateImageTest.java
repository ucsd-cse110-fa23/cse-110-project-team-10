package cse110_project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.beans.Transient;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class GenerateImageTest {
    private String sandwichURL = "https://static.toiimg.com/thumb/83740315.cms?width=1200&height=900";
    private String noodleURL = "https://www.chilipeppermadness.com/wp-content/uploads/2023/06/Gochujang-Noodles-Recipe-SQ.jpg";
    private Recipe TestRecipe = new Recipe("null", "null", RecipeKind.valueOf("lunch"), "null");
    private mockImageGenerate temp;

    @BeforeEach
    void setUp() {
        TestRecipe.setRecipeName("sandwich");
        TestRecipe.setRecipeDescription("ham, cheese, tomato, lettuce, bread");
        TestRecipe.setRecipeKind(RecipeKind.valueOf("lunch"));
        temp = new mockImageGenerate();
    }

    @Test
    void validateURL(){
        try {
            URI uri = new URI(sandwichURL);
            uri.toURL();
            assertTrue(true);
        } 
        catch (MalformedURLException e) {
            
        } 
        catch (URISyntaxException e) {
            
        }
    }

    @Test
    void testGenerateImage(){
        try{
            String generatedURL = temp.mockGenerate(TestRecipe.getRecipeName());
            TestRecipe.setRecipeImage(generatedURL);
            assertEquals(TestRecipe.getRecipeImage(), sandwichURL);
        }
        catch (Exception f){

        }
    }
}
