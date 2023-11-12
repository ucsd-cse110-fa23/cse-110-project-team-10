package cse110_project;
// the names of these enums are shown in UI so should be nice and not programmery. If changed have to update
// they're lower case so that the AI's outputs can be consistent by calling .toLower() on it
enum RecipeKind {
    breakfast,
    lunch,
    dinner
}

public class Recipe {
    private RecipeKind kind;
    private String name = "";
    private String description = "";

    Recipe(String name, String description, RecipeKind kind) {
        this.name = name;
        this.description = description;
        this.kind = kind;
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