package keqing.pollution.loaders.recipes;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import keqing.pollution.Pollution;
import keqing.pollution.api.unification.PollutionMaterials;
import keqing.pollution.common.items.PollutionMetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.items.ItemsTC;
import gregtech.api.unification.ore.OrePrefix;

import static gregtech.api.unification.material.Materials.*;


public class ThaumcraftRecipes {
    public static void init(){
        catalyst();

    }
    private static void catalyst() {
        //活性催化粗胚，搅拌机
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .fluidInputs(Mercury.getFluid(1000))
                .input(OrePrefix.dust, Materials.Amethyst)
                .input(OrePrefix.dust, Materials.CertusQuartz)
                .input(OrePrefix.dust, Materials.Opal)
                .input(OrePrefix.dust, Materials.Salt)
                .input(OrePrefix.dust, Materials.Sulfur)
                .output(OrePrefix.dust, PollutionMaterials.roughdraft, 6)
                .duration(600)
                .EUt(120)
                .buildAndRegister();

        //魔力催化剂基底，搅拌机
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, PollutionMaterials.roughdraft, 6)
                .input(OrePrefix.dust, PollutionMaterials.infused_air)
                .input(OrePrefix.dust, PollutionMaterials.infused_fire)
                .input(OrePrefix.dust, PollutionMaterials.infused_entropy)
                .output(OrePrefix.dust, PollutionMaterials.substrate, 9)
                .duration(600)
                .EUt(120)
                .buildAndRegister();

        //空白催化核心，注魔
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "blank_catalyst_core"), new InfusionRecipe(
                "",
                new ItemStack(PollutionMetaItems.BLANKCORE.getMetaItem(), 1, 2),
                6,
                new AspectList().add(Aspect.ALCHEMY, 128).add(Aspect.EXCHANGE, 64).add(Aspect.MAGIC, 128).add(Aspect.ENERGY, 32),
                "gemValonite",
                "blockSubstrate",
                "blockSubstrate",
                "gemExquisiteAmethyst",
                "gemExquisiteOpal",
                new ItemStack(ItemsTC.visResonator),
                new ItemStack(ItemsTC.morphicResonator),
                new ItemStack(ItemsTC.causalityCollapser),
                new ItemStack(BlocksTC.visBattery)));

        //冷热催化核心，注魔
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "hot_catalyst_core"), new InfusionRecipe(
                "",
                new ItemStack(PollutionMetaItems.HOTCORE.getMetaItem(), 1, 3),
                3,
                new AspectList().add(Aspect.FIRE, 128).add(Aspect.AURA, 32),
                new ItemStack(PollutionMetaItems.BLANKCORE.getMetaItem(), 1, 2),
                "gemExquisiteRuby",
                "gemExquisiteRuby",
                "oreCrystalFire",
                "oreCrystalFire",
                new ItemStack(Items.BLAZE_POWDER),
                new ItemStack(Items.BLAZE_POWDER),
                "plateOctine",
                "plateOctine"));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "cold_catalyst_core"), new InfusionRecipe(
                "",
                new ItemStack(PollutionMetaItems.COLDCORE.getMetaItem(), 1, 4),
                3,
                new AspectList().add(Aspect.COLD, 128).add(Aspect.AURA, 32),
                new ItemStack(PollutionMetaItems.BLANKCORE.getMetaItem(), 1, 2),
                "gemExquisiteSapphire",
                "gemExquisiteSapphire",
                "oreCrystalWater",
                "oreCrystalWater",
                new ItemStack(Blocks.SNOW),
                new ItemStack(Blocks.SNOW),
                new ItemStack(Blocks.ICE),
                new ItemStack(Blocks.ICE)));

        //凝聚催化核心，注魔
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "integration_catalyst_core"), new InfusionRecipe(
                "",
                new ItemStack(PollutionMetaItems.INTEGRATECORE.getMetaItem(), 1, 4),
                5,
                new AspectList().add(Aspect.ORDER, 128).add(Aspect.AURA, 64),
                new ItemStack(PollutionMetaItems.BLANKCORE.getMetaItem(), 1, 2),
                "gemExquisiteDiamond",
                "gemExquisiteDiamond",
                "oreCrystalOrder",
                "oreCrystalOrder",
                new ItemStack(BlocksTC.logSilverwood),
                new ItemStack(BlocksTC.logSilverwood),
                "plateLead",
                "plateLead"));

        //分离催化核心，注魔
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "segregation_catalyst_core"), new InfusionRecipe(
                "",
                new ItemStack(PollutionMetaItems.SEGREGATECORE.getMetaItem(), 1, 5),
                5,
                new AspectList().add(Aspect.ENTROPY, 128).add(Aspect.AURA, 64),
                new ItemStack(PollutionMetaItems.BLANKCORE.getMetaItem(), 1, 2),
                new ItemStack(Items.ENDER_EYE),
                new ItemStack(Items.ENDER_EYE),
                "oreCrystalEntropy",
                "oreCrystalEntropy",
                new ItemStack(ItemsTC.voidSeed),
                new ItemStack(ItemsTC.voidSeed),
                "dustGunpowder",
                "dustGunpowder"));
    }
}
