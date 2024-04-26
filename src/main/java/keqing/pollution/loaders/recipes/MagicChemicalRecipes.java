package keqing.pollution.loaders.recipes;

import com.cleanroommc.groovyscript.api.IIngredient;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.unification.GCYSMaterials;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.loaders.recipes.GTQTRecipes;
import keqing.pollution.api.recipes.PORecipeMaps;
import keqing.pollution.api.unification.PollutionMaterials;
import keqing.pollution.common.items.PollutionMetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MagicChemicalRecipes {
    public static void init() {
            chemical();
        }

        private static void chemical(){

            //四种催化剂的更简单的配方
            PORecipeMaps.MAGIC_CHEMICAL_REACTOR_RECIPES.recipeBuilder()
                    .inputs(new ItemStack(PollutionMetaItems.BLANKCORE.getMetaItem(), 1, 2))
                    .fluidInputs(PollutionMaterials.infused_fire.getFluid(2304))
                    .notConsumable(new ItemStack(PollutionMetaItems.HOTCORE.getMetaItem(), 1, 3))
                    .outputs(new ItemStack(PollutionMetaItems.HOTCORE.getMetaItem(), 1, 3))
                    .duration(3600)
                    .EUt(480)
                    .buildAndRegister();
            PORecipeMaps.MAGIC_CHEMICAL_REACTOR_RECIPES.recipeBuilder()
                    .inputs(new ItemStack(PollutionMetaItems.BLANKCORE.getMetaItem(), 1, 2))
                    .fluidInputs(PollutionMaterials.infused_cold.getFluid(2304))
                    .notConsumable(new ItemStack(PollutionMetaItems.COLDCORE.getMetaItem(), 1, 4))
                    .outputs(new ItemStack(PollutionMetaItems.COLDCORE.getMetaItem(), 1, 4))
                    .duration(3600)
                    .EUt(480)
                    .buildAndRegister();
            PORecipeMaps.MAGIC_CHEMICAL_REACTOR_RECIPES.recipeBuilder()
                    .inputs(new ItemStack(PollutionMetaItems.BLANKCORE.getMetaItem(), 1, 2))
                    .fluidInputs(PollutionMaterials.infused_alchemy.getFluid(2304))
                    .notConsumable(new ItemStack(PollutionMetaItems.INTEGRATECORE.getMetaItem(), 1, 5))
                    .outputs(new ItemStack(PollutionMetaItems.INTEGRATECORE.getMetaItem(), 1, 5))
                    .duration(3600)
                    .EUt(480)
                    .buildAndRegister();
            PORecipeMaps.MAGIC_CHEMICAL_REACTOR_RECIPES.recipeBuilder()
                    .inputs(new ItemStack(PollutionMetaItems.BLANKCORE.getMetaItem(), 1, 2))
                    .fluidInputs(PollutionMaterials.infused_alchemy.getFluid(2304))
                    .notConsumable(new ItemStack(PollutionMetaItems.SEGREGATECORE.getMetaItem(), 1, 6))
                    .outputs(new ItemStack(PollutionMetaItems.SEGREGATECORE.getMetaItem(), 1, 6))
                    .duration(3600)
                    .EUt(480)
                    .buildAndRegister();


            //可燃冰→石墨烯+氢
            PORecipeMaps.MAGIC_CHEMICAL_REACTOR_RECIPES.recipeBuilder()
                    .input(OrePrefix.dust, GTQTMaterials.Gashydrate, 4)
                    .output(OrePrefix.dust, Materials.Graphene, 1)
                    .fluidOutputs(Materials.Methane.getFluid(9600))
                    .fluidOutputs(Materials.Water.getFluid(4800))
                    .duration(200)
                    .EUt(30720)
                    .buildAndRegister();
            //10H2O+N2+S=H2SO4+2HNO3+8H2, EV
            PORecipeMaps.MAGIC_CHEMICAL_REACTOR_RECIPES.recipeBuilder()
                    .input(OrePrefix.dust, Materials.Sulfur, 1)
                    .fluidInputs(Materials.Water.getFluid(10000))
                    .fluidInputs(Materials.Nitrogen.getFluid(1000))
                    .fluidOutputs(Materials.SulfuricAcid.getFluid(1000))
                    .fluidOutputs(Materials.NitricAcid.getFluid(2000))
                    .fluidOutputs(Materials.Hydrogen.getFluid(8000))
                    .duration(200)
                    .EUt(1920)
                    .buildAndRegister();
            //6NaCl+2C+3N2+8H20+5H2=2NaOH+2Na2CO3+6NH4Cl, EV
            PORecipeMaps.MAGIC_CHEMICAL_REACTOR_RECIPES.recipeBuilder()
                    .input(OrePrefix.dust, Materials.Carbon, 2)
                    .input(OrePrefix.dust, Materials.Salt, 6)
                    .fluidInputs(Materials.Water.getFluid(8000))
                    .fluidInputs(Materials.Nitrogen.getFluid(3000))
                    .fluidInputs(Materials.Hydrogen.getFluid(5000))
                    .output(OrePrefix.dust, Materials.SodiumHydroxide, 2)
                    .output(OrePrefix.dust, Materials.SodaAsh, 2)
                    .output(OrePrefix.dust, Materials.AmmoniumChloride, 6)
                    .duration(200)
                    .EUt(1920)
                    .buildAndRegister();

            //SiCl4+4C2H5OH=(C2H5O)4Si+4HCl
            RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                    .fluidInputs(GCYSMaterials.SiliconTetrachloride.getFluid(1000))
                    .fluidInputs(Materials.Ethanol.getFluid(4000))
                    .fluidOutputs(PollutionMaterials.ethyl_silicate.getFluid(1000))
                    .fluidOutputs(Materials.HydrochloricAcid.getFluid(4000))
                    .circuitMeta(1)
                    .duration(100)
                    .EUt(120)
                    .buildAndRegister();
            //荷叶粉
            RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                    .input(Blocks.WATERLILY, 1)
                    .output(OrePrefix.dust, PollutionMaterials.lotus_dust)
                    .duration(200)
                    .EUt(8)
                    .buildAndRegister();
            //LLP粗胚
            RecipeMaps.MIXER_RECIPES.recipeBuilder()
                    .fluidInputs(PollutionMaterials.ethyl_silicate.getFluid(4000))
                    .input(OrePrefix.dust, PollutionMaterials.lotus_dust, 4)
                    .output(OrePrefix.dust, PollutionMaterials.rough_llp)
                    .duration(400)
                    .EUt(120)
                    .buildAndRegister();
            //LLP@SiO2
            PORecipeMaps.MAGIC_CHEMICAL_REACTOR_RECIPES.recipeBuilder()
                    .input(OrePrefix.dust, PollutionMaterials.rough_llp, 4)
                    .fluidInputs(PollutionMaterials.infused_water.getFluid(2304))
                    .output(OrePrefix.dust, PollutionMaterials.llp, 1)
                    .duration(2000)
                    .EUt(480)
                    .buildAndRegister();
            //LLP原油悬浊液
            RecipeMaps.MIXER_RECIPES.recipeBuilder()
                    .fluidInputs(Materials.RawOil.getFluid(1000))
                    .input(OrePrefix.dust, PollutionMaterials.llp, 1)
                    .fluidOutputs(PollutionMaterials.oil_with_llp.getFluid(1000))
                    .duration(20)
                    .EUt(120)
                    .buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder()
                    .fluidInputs(Materials.OilLight.getFluid(1000))
                    .input(OrePrefix.dust, PollutionMaterials.llp, 1)
                    .fluidOutputs(PollutionMaterials.oil_with_llp.getFluid(1000))
                    .duration(20)
                    .EUt(120)
                    .buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder()
                    .fluidInputs(Materials.OilHeavy.getFluid(1000))
                    .input(OrePrefix.dust, PollutionMaterials.llp, 1)
                    .fluidOutputs(PollutionMaterials.oil_with_llp.getFluid(1000))
                    .duration(20)
                    .EUt(120)
                    .buildAndRegister();
            RecipeMaps.MIXER_RECIPES.recipeBuilder()
                    .fluidInputs(Materials.Oil.getFluid(1000))
                    .input(OrePrefix.dust, PollutionMaterials.llp, 1)
                    .fluidOutputs(PollutionMaterials.oil_with_llp.getFluid(1000))
                    .duration(20)
                    .EUt(120)
                    .buildAndRegister();
            //离心循环LLP
            RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder()
                    .fluidInputs(PollutionMaterials.oil_with_llp.getFluid(1000))
                    .output(OrePrefix.dust, PollutionMaterials.llp, 1)
                    .fluidOutputs(GTQTMaterials.PreTreatedCrudeOil.getFluid(1000))
                    .fluidOutputs(Materials.SaltWater.getFluid(200))
                    .duration(20)
                    .EUt(120)
                    .buildAndRegister();


    }
}
