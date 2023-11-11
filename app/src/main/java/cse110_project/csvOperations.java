package cse110_project;

import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;

public class csvOperations {
    private ArrayList<Recipe> current_recipes;

    csvOperations(ArrayList<Recipe> current) {
        current_recipes = current;
    }

    public String writeToJSON() {
        JSONObject cr; 

        //takes arraylist and makes json file
        for (Recipe r : current_recipes) {
            cr = new JSONObject();
            cr.put("name", r.getRecipeName());
            cr.put("description", r.getRecipeDescription());
            cr.put("kind", r.getRecipeKind().name());

            try (FileWriter fw = new FileWriter("recipes.json")) {
                fw.write(cr.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public ArrayList<Recipe> readFromJSON() {
        ArrayList<Recipe> tbr = new ArrayList<Recipe>();
        if (!Files.exists(Paths.get("recipes.json"))) {
            try {
                Files.createFile(Paths.get("recipes.json"));
            } catch(IOException e) {
                //
            }
            return tbr; 
        } else {
            /* 
            try (JsonReader r = Json.createReader(new FileReader("recipes.json"))) {
                // Read the JSON array from the file
                JsonArray jsonArray = r.readArray();

                // read each recipe into a recipe object, then insert recipe objects into arraylist
                for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
                    String name = jsonObject.getString("name");
                    String description = jsonObject.getString("description");
                    String skind = jsonObject.getString("kind");

                    RecipeKind kind =RecipeKind.valueOf(skind);

                    Recipe tempRecipe = new Recipe(name, description, kind);
                    tbr.add(tempRecipe);
                }
                return tbr;
            } catch (IOException e) {
                e.printStackTrace();
            }
            */
            return tbr;
        }
    }
}
