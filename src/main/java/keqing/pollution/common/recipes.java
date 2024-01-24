package keqing.pollution.common;

import gregtech.api.recipes.ModHandler;
import keqing.pollution.common.metatileentity.PollutionMetaTileEntities;

import static gregtech.loaders.recipe.CraftingComponent.*;
import static gregtech.loaders.recipe.MetaTileEntityLoader.registerMachineRecipe;

public class recipes {
    public static void init() {
        Muffler();
    }

    private static void Muffler() {
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.muffler_hatch.lv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.muffler_hatch.mv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.muffler_hatch.hv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.muffler_hatch.ev");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.muffler_hatch.iv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.muffler_hatch.luv");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.muffler_hatch.zpm");
        ModHandler.removeRecipeByName("gregtech:gregtech.machine.muffler_hatch.uv");

        registerMachineRecipe(PollutionMetaTileEntities.FLUX_MUFFLERS, "HM", "PR",
                'H', HULL,
                'M', MOTOR,
                'P', PIPE_NORMAL,
                'R', ROTOR);

    }
}
