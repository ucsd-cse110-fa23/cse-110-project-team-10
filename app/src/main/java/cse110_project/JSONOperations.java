package cse110_project;

import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;

public class JSONOperations {
    private ArrayList<Recipe> current_recipes;

    JSONOperations(ArrayList<Recipe> current) {
        current_recipes = current;
    }

    public void writeToJSON() {
        JSONObject cr; 

        //takes arraylist and makes json file
        try (FileWriter fw = new FileWriter("recipes.json")) {
            for (Recipe r : current_recipes) {
                cr = new JSONObject();
                cr.put("name", r.getRecipeName());
                cr.put("description", r.getRecipeDescription());
                cr.put("kind", r.getRecipeKind().name());
                fw.write(cr.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
    public ArrayList<Recipe> readFromJSON() {
        ArrayList<Recipe> temp = new ArrayList<Recipe>();
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("recipes.json")));
            System.out.print("recipes.json found");
            ArrayList<Recipe> recipes = new ArrayList<Recipe>();
            Pattern pattern = Pattern.compile("\\{([^}]+)\\}");
            Matcher matcher = pattern.matcher(jsonString);
            String subName = "\",\"name\":\"";
            String subDesc = "\",\"description\":\"";
            String subKind = "\"kind\":\"";
            while (matcher.find()) {
                String jsonObject = matcher.group(1); //should contain each set
                //System.out.println(jsonObject);
                int idxName = jsonObject.indexOf(subName);
                int idxDesc = jsonObject.indexOf(subDesc);
                int idxKind = jsonObject.indexOf(subKind);
                //System.out.println(idxName + " " + idxDesc + " " + idxKind);
                String jsonName = jsonObject.substring(idxName + subName.length(), idxDesc);
                String jsonDesc = jsonObject.substring(idxDesc + subDesc.length(), jsonObject.lastIndexOf("\""));
                String jsonKindStr = jsonObject.substring(idxKind + subKind.length(), idxName);
                RecipeKind jsonKind = RecipeKind.valueOf(jsonKindStr);
                //System.out.println("jsonName: " + jsonName + " jsonDesc: " + jsonDesc + " jsonKind: " + jsonKindStr);
                recipes.add(new Recipe(jsonName, jsonDesc, jsonKind));
            }
            //System.out.println("final size: " + recipes.size());
            return recipes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }
}
