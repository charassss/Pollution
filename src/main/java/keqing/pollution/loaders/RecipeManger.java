package keqing.pollution.loaders;

import keqing.pollution.loaders.recipes.AERecipes;
import keqing.pollution.loaders.recipes.MachineRecipes;

public class RecipeManger {
    public static void init() {
        MachineRecipes.init();
        AERecipes.init();
    }


}
