package cse110_project;

import main.java.cse110_project.Recipe;

public class jsonLoadAndSaveTest {
    private ArrayList<Recipe> testRecipeList;
    private String rName;
    private String rDesc;
    private RecipeKind rKind;

    @BeforeEach
    void setup(){
        for (int i = 0; i < 5; i++) {
            rName = "Recipe #" + i;
            rDesc = "";
            rKind = RecipeKind.valueOf("Dinner");
            Recipe toAdd = new Recipe(rName, rDesc, rKind);
            testRecipeList.add(toAdd);
        }
    }
    @Test
    void saveToJSON(){
         //take arraylist and save to json
         
    }
    @Test
    void loadFromJSON(){

    }
}