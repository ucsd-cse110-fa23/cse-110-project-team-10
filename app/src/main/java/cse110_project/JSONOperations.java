package cse110_project;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;

public class JSONOperations {
    public static JSONObject intoJSON(Recipe recipe) {
        JSONObject ret = new JSONObject();
        ret.put("name", recipe.getRecipeName());
        ret.put("description", recipe.getRecipeDescription());
        ret.put("kind", recipe.getRecipeKind().name());
        return ret;
    }

    public static Recipe fromJSON(JSONObject obj) {
        return new Recipe(obj.getString("name"), obj.getString("description"), RecipeKind.valueOf(obj.getString("kind")));
    }

    public static String intoJSONString(RecipeStateManager manager) {
        JSONObject cr;

        cr = new JSONObject();
        ArrayList<JSONObject> asObjects = new ArrayList<JSONObject>();
        for(Recipe r : manager.getRecipes()) {
            asObjects.add(intoJSON(r));
        }
        cr.put("recipes", asObjects);

        return cr.toString();
    }

    public static RecipeStateManager fromJSONString(String jsonString) {
        RecipeStateManager ret = new RecipeStateManager();
        JSONObject rootObject = new JSONObject(jsonString);
        JSONArray arr = rootObject.getJSONArray("recipes");
        for (int i = arr.length() - 1; i >= 0; i--) { // iterates backwards because addRecipe adds in reverse order
            JSONObject recipeJSON = arr.getJSONObject(i);
            Recipe recipe = fromJSON(recipeJSON);
            ret.addRecipe(recipe);
        }
        return ret;
    }
}
