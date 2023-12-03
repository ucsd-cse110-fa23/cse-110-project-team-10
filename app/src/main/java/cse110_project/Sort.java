package cse110_project;

import cse110_project.Recipe;
import cse110_project.RecipeStateManager;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.*;

public class Sort {
    Sort(){}

    public ArrayList<Recipe> sortDefault(ArrayList<Recipe> list){
        return list;
    }

    public ArrayList<Recipe> sortOldest(ArrayList<Recipe> list){
        Collections.reverse(list);
        return list;
    }

    public ArrayList<Recipe> sortAlphabetOrder(ArrayList<Recipe> list){
        Collections.sort(list, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe r1, Recipe r2) {
                return r1.getRecipeName().toLowerCase().compareTo(r2.getRecipeName().toLowerCase());
            }
        });
        return list;
    }

    public ArrayList<Recipe> sortReverseAlphabet(ArrayList<Recipe> list){
        Collections.sort(list, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe r1, Recipe r2) {
                return r2.getRecipeName().toLowerCase().compareTo(r1.getRecipeName().toLowerCase());
            }
        });
        return list;
    }
}
