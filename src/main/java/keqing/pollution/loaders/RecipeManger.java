package keqing.pollution.loaders;

import keqing.pollution.loaders.recipes.AERecipes;
import keqing.pollution.loaders.recipes.MachineRecipes;
import keqing.pollution.loaders.recipes.MagicGCYMRecipes;
import keqing.pollution.loaders.recipes.ThaumcraftRecipes;
import keqing.pollution.loaders.recipes.CompoundAspectRecipes;

public class RecipeManger {
    public static void init() {
        MachineRecipes.init();
        AERecipes.init();
        ThaumcraftRecipes.init();
        MagicGCYMRecipes.init();
        CompoundAspectRecipes.init();
    }


}
