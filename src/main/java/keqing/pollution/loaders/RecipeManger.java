package keqing.pollution.loaders;

import keqing.pollution.loaders.recipes.*;

public class RecipeManger {
    public static void init() {
        MachineRecipes.init();
        AERecipes.init();
        InfusedManager.init();
        CompoundAspectRecipes.init();
        MagicGCYMRecipes.init();
        ThaumcraftRecipes.init();
    }


}
