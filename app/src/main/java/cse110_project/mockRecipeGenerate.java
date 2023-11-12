package cse110_project;

class mockRecipeGenerate {
    private String recipe_type;
    private String recipe_list;

    public mockRecipeGenerate(String rt, String rl) {
        recipe_type = rt;
        recipe_list = rl;
    }

    public String generate() {
        String generateOut = "Here is a "+recipe_type+" meal that will use the following ingredients: "+recipe_list;
        return generateOut;
    }
}
