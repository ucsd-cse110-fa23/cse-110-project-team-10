package cse110_project;

import java.util.ArrayList;

import cse110_project.Filter;
import cse110_project.Recipe;
import cse110_project.RecipeStateManager;

public class Modify {
    private final String DEFAULT = "default";
    private final String NEWEST = "default: most recent";
    private final String OLDEST = "least recent";
    private final String ALPHABET = "a-z";

    private Filter filter;
    private Sort sort;
    private RecipeStateManager recipeList;

    Modify(RecipeStateManager inputList){
        this.filter = new Filter(inputList);
        this.sort = new Sort();
        this.recipeList = new RecipeStateManager(inputList.getRecipes());
    }

    public RecipeStateManager modify(String mealType, String category){
        RecipeStateManager newList;
        ArrayList<Recipe> sortedList = new ArrayList<>(recipeList.getRecipes());

        if(mealType.equals(DEFAULT) && category.equals(NEWEST)){
            return recipeList;
        }
        else if(mealType.equals(DEFAULT)){
            if(category.equals(NEWEST)){
                sortedList = sort.sortDefault(sortedList);
            }
            else if(category.equals(OLDEST)){
                sortedList = sort.sortOldest(sortedList);
            }
            else if(category.equals(ALPHABET)){
                sortedList = sort.sortAlphabetOrder(sortedList);
            }
            else{
                sortedList = sort.sortReverseAlphabet(sortedList);
            }
            newList = new RecipeStateManager(sortedList);
        }
        else{
            newList = filter.filterType(mealType);
            if(category.equals(NEWEST)){
                sortedList = sort.sortDefault(newList.getRecipes());
            }
            else if(category.equals(OLDEST)){
                sortedList = sort.sortOldest(newList.getRecipes());
            }
            else if(category.equals(ALPHABET)){
                sortedList = sort.sortAlphabetOrder(newList.getRecipes());
            }
            else{
                sortedList = sort.sortReverseAlphabet(newList.getRecipes());
            }
            newList = new RecipeStateManager(sortedList);
        }

        return newList;
        
    }
}