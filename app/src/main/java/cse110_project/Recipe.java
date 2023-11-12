package cse110_project;
// the names of these enums are shown in UI so should be nice and not programmery. If changed have to update
enum RecipeKind {
    Breakfast,
    Lunch,
    Dinner
}

public class Recipe {
    private RecipeKind kind;
    private String name = "";
    private String description = "";

    Recipe(String n, String i, RecipeKind rk) {
        name = n;
        description = i;
        kind = rk;
    }

    public String getRecipeName(){
        return name;
    }
    public String getRecipeDescription(){
        return description;
    }
    public RecipeKind getRecipeKind(){
        return kind;
    }
    public void setRecipeName(String newName){
        name = newName;
    }
    public void setRecipeDescription(String newDesc){
        description = newDesc;
    }
    public void setRecipeKind(RecipeKind newKind){
        kind = newKind;
    }
}