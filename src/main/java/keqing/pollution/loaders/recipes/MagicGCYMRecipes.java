package keqing.pollution.loaders.recipes;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.pollution.Pollution;
import keqing.pollution.api.unification.PollutionMaterials;
import keqing.pollution.common.block.PollutionMetaBlock.POMBeamCore;
import keqing.pollution.common.block.PollutionMetaBlock.POMagicBlock;
import keqing.pollution.common.block.PollutionMetaBlocks;
import keqing.pollution.common.items.PollutionMetaItem1;
import keqing.pollution.common.items.PollutionMetaItems;
import keqing.pollution.common.metatileentity.PollutionMetaTileEntities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.Thaumcraft;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import gregtech.api.unification.ore.OrePrefix;

import java.nio.charset.StandardCharsets;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static keqing.pollution.api.recipes.PORecipeMaps.MAGIC_ALLOY_BLAST_RECIPES;
import static keqing.pollution.api.unification.PollutionMaterials.perditioaluminium;

public class MagicGCYMRecipes {
    public static void init() {
        materials();
    }

    private static void materials(){
        //这里是六个基础外壳材料的搅拌机配方
        //风要素-律动钛
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Bauxite,  2)
                .input(OrePrefix.dust, Aluminium, 1)
                .input(OrePrefix.dust, Manganese, 1)
                .input(OrePrefix.dust, PollutionMaterials.infused_air, 5)
                .output(OrePrefix.dust, PollutionMaterials.aertitanium, 9)
                .duration(900)
                .EUt(480)
                .buildAndRegister();

        MAGIC_ALLOY_BLAST_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Bauxite,  2)
                .input(OrePrefix.dust, Aluminium, 1)
                .input(OrePrefix.dust, Manganese, 1)
                .input(OrePrefix.dust, PollutionMaterials.infused_air, 5)
                .fluidOutputs(PollutionMaterials.aertitanium.getFluid(9*144))
                .duration(300)
                .blastFurnaceTemp(2700)
                .EUt(480)
                .buildAndRegister();
        //火要素-残日钢
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Steel,  2)
                .input(OrePrefix.dust, Magnesium, 1)
                .input(OrePrefix.dust, Lithium, 1)
                .input(OrePrefix.dust, PollutionMaterials.infused_fire, 5)
                .output(OrePrefix.dust, PollutionMaterials.ignissteel, 9)
                .duration(900)
                .EUt(480)
                .buildAndRegister();

        MAGIC_ALLOY_BLAST_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Steel,  2)
                .input(OrePrefix.dust, Magnesium, 1)
                .input(OrePrefix.dust, Lithium, 1)
                .input(OrePrefix.dust, PollutionMaterials.infused_fire, 5)
                .fluidOutputs(PollutionMaterials.ignissteel.getFluid(9*144))
                .duration(300)
                .blastFurnaceTemp(2700)
                .EUt(480)
                .buildAndRegister();
        //水要素-捩花银
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Silver,  2)
                .input(OrePrefix.dust, Tin, 1)
                .fluidInputs(Mercury.getFluid(1000))
                .input(OrePrefix.dust, PollutionMaterials.infused_water, 5)
                .output(OrePrefix.dust, PollutionMaterials.aquasilver, 9)
                .duration(900)
                .EUt(480)
                .buildAndRegister();

        MAGIC_ALLOY_BLAST_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Silver,  2)
                .input(OrePrefix.dust, Tin, 1)
                .fluidInputs(Mercury.getFluid(1000))
                .input(OrePrefix.dust, PollutionMaterials.infused_water, 5)
                .fluidOutputs(PollutionMaterials.aquasilver.getFluid(9*144))
                .duration(300)
                .blastFurnaceTemp(2700)
                .EUt(480)
                .buildAndRegister();
        //地要素-定坤铜
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Copper,  2)
                .input(OrePrefix.dust, Boron, 1)
                .input(OrePrefix.dust, Carbon, 1)
                .input(OrePrefix.dust, PollutionMaterials.infused_earth, 5)
                .output(OrePrefix.dust, PollutionMaterials.terracopper, 9)
                .duration(900)
                .EUt(480)
                .buildAndRegister();

        MAGIC_ALLOY_BLAST_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Copper,  2)
                .input(OrePrefix.dust, Boron, 1)
                .input(OrePrefix.dust, Carbon, 1)
                .input(OrePrefix.dust, PollutionMaterials.infused_earth, 5)
                .fluidOutputs(PollutionMaterials.terracopper.getFluid(9*144))
                .duration(300)
                .blastFurnaceTemp(2700)
                .EUt(480)
                .buildAndRegister();
        //秩序要素-司辰铅
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Lead,  2)
                .input(OrePrefix.dust, Silicon, 1)
                .input(OrePrefix.dust, Gold, 1)
                .input(OrePrefix.dust, PollutionMaterials.infused_order, 5)
                .output(OrePrefix.dust, PollutionMaterials.ordolead, 9)
                .duration(900)
                .EUt(480)
                .buildAndRegister();

        MAGIC_ALLOY_BLAST_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Lead,  2)
                .input(OrePrefix.dust, Silicon, 1)
                .input(OrePrefix.dust, Gold, 1)
                .input(OrePrefix.dust, PollutionMaterials.infused_order, 5)
                .fluidOutputs(PollutionMaterials.ordolead.getFluid(9*144))
                .duration(300)
                .blastFurnaceTemp(2700)
                .EUt(480)
                .buildAndRegister();
        //混沌要素-无极铝
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Aluminium,  2)
                .fluidInputs(Fluorine.getFluid(1000))
                .input(OrePrefix.dust, Thorium, 1)
                .input(OrePrefix.dust, PollutionMaterials.infused_entropy, 5)
                .output(OrePrefix.dust, perditioaluminium, 9)
                .duration(900)
                .EUt(480)
                .buildAndRegister();

        MAGIC_ALLOY_BLAST_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Aluminium,  2)
                .fluidInputs(Fluorine.getFluid(1000))
                .input(OrePrefix.dust, Thorium, 1)
                .input(OrePrefix.dust, PollutionMaterials.infused_entropy, 5)
                .fluidOutputs(PollutionMaterials.perditioaluminium.getFluid(9*144))
                .duration(300)
                .blastFurnaceTemp(2700)
                .EUt(480)
                .buildAndRegister();
        //高炉2700°烧六种合金捏

        //这里是六个外壳的制作配方
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, PollutionMaterials.aertitanium, 6)
                .input(OrePrefix.frameGt, PollutionMaterials.aertitanium, 1)
                .outputs(PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_AIR))
                .circuitMeta(6)
                .duration(300)
                .EUt(480)
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, PollutionMaterials.ignissteel, 6)
                .input(OrePrefix.frameGt, PollutionMaterials.ignissteel, 1)
                .outputs(PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_HOT))
                .circuitMeta(6)
                .duration(300)
                .EUt(480)
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, PollutionMaterials.aquasilver, 6)
                .input(OrePrefix.frameGt, PollutionMaterials.aquasilver, 1)
                .outputs(PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_WATER))
                .circuitMeta(6)
                .duration(300)
                .EUt(480)
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, PollutionMaterials.terracopper, 6)
                .input(OrePrefix.frameGt, PollutionMaterials.terracopper, 1)
                .outputs(PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_EARTH))
                .circuitMeta(6)
                .duration(300)
                .EUt(480)
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, PollutionMaterials.ordolead, 6)
                .input(OrePrefix.frameGt, PollutionMaterials.ordolead, 1)
                .outputs(PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_ORDER))
                .circuitMeta(6)
                .duration(300)
                .EUt(480)
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, perditioaluminium, 6)
                .input(OrePrefix.frameGt, perditioaluminium, 1)
                .outputs(PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.VOID_PRISM))
                .circuitMeta(6)
                .duration(300)
                .EUt(480)
                .buildAndRegister();
        //这里是六个主方块的注魔配方
        //配方暂定造价：主注魔材料催化剂，次要材料mv电路板*4+对应要素的水晶+mv级别的对应小机器一台+一个对应外壳+法罗钠晶体一个
        //离心
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_centrifuge"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaTileEntities.MAGIC_CENTRIFUGE.getStackForm(),
                5,
                new AspectList().add(Aspect.AIR, 250).add(Aspect.MAGIC, 64).add(Aspect.MECHANISM, 128),
                new ItemStack(PollutionMetaItems.SEGREGATECORE.getMetaItem(), 1, 6),
                "gemValonite",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "oreCrystalAir",
                MetaTileEntities.CENTRIFUGE[MV].getStackForm(),
                MetaTileEntities.THERMAL_CENTRIFUGE[MV].getStackForm(),
                PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_AIR)));
        //高炉
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_blast"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaTileEntities.MAGIC_ELECTRIC_BLAST_FURNACE.getStackForm(),
                5,
                new AspectList().add(Aspect.FIRE, 250).add(Aspect.MAGIC, 64).add(Aspect.MECHANISM, 128),
                new ItemStack(PollutionMetaItems.HOTCORE.getMetaItem(), 1, 3),
                "gemValonite",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "oreCrystalFire",
                MetaTileEntities.ELECTRIC_FURNACE[MV].getStackForm(),
                PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_HOT)));
        //洗矿机
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_chemical_bath"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaTileEntities.MAGIC_CHEMICAL_BATH.getStackForm(),
                5,
                new AspectList().add(Aspect.WATER, 250).add(Aspect.MAGIC, 64).add(Aspect.MECHANISM, 128),
                new ItemStack(PollutionMetaItems.COLDCORE.getMetaItem(), 1, 4),
                "gemValonite",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "oreCrystalWater",
                MetaTileEntities.CHEMICAL_BATH[MV].getStackForm(),
                MetaTileEntities.ORE_WASHER[MV].getStackForm(),
                PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_WATER)));
        //研磨机
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_macerator"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaTileEntities.MAGIC_MACERATOR.getStackForm(),
                5,
                new AspectList().add(Aspect.EARTH, 250).add(Aspect.MAGIC, 64).add(Aspect.MECHANISM, 128),
                new ItemStack(PollutionMetaItems.SEGREGATECORE.getMetaItem(), 1, 6),
                "gemValonite",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "oreCrystalEarth",
                MetaTileEntities.MACERATOR[MV].getStackForm(),
                PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_EARTH)));
        //电解机
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_electrolyzer"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaTileEntities.MAGIC_ELECTROLYZER.getStackForm(),
                5,
                new AspectList().add(Aspect.ORDER, 250).add(Aspect.MAGIC, 64).add(Aspect.MECHANISM, 128),
                new ItemStack(PollutionMetaItems.SEGREGATECORE.getMetaItem(), 1, 6),
                "gemValonite",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "oreCrystalOrder",
                MetaTileEntities.ELECTROLYZER[MV].getStackForm(),
                PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_ORDER)));
        //搅拌机
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_mixer"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaTileEntities.MAGIC_MIXER.getStackForm(),
                5,
                new AspectList().add(Aspect.ENTROPY, 250).add(Aspect.MAGIC, 64).add(Aspect.MECHANISM, 128),
                new ItemStack(PollutionMetaItems.INTEGRATECORE.getMetaItem(), 1, 5),
                "gemValonite",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "oreCrystalEntropy",
                MetaTileEntities.MIXER[MV].getStackForm(),
                PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.VOID_PRISM)));
        //这里是魔力钢、漫宿钢等的配方
        //不纯魔力搅拌
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, PollutionMaterials.infused_order)
                .input(OrePrefix.dust, PollutionMaterials.infused_entropy)
                .fluidOutputs(PollutionMaterials.impuremana.getFluid(18))
                .circuitMeta(1)
                .duration(100)
                .EUt(120)
                .buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, PollutionMaterials.infused_air)
                .input(OrePrefix.dust, PollutionMaterials.infused_earth)
                .fluidOutputs(PollutionMaterials.impuremana.getFluid(18))
                .circuitMeta(1)
                .duration(100)
                .EUt(120)
                .buildAndRegister();
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, PollutionMaterials.infused_fire)
                .input(OrePrefix.dust, PollutionMaterials.infused_water)
                .fluidOutputs(PollutionMaterials.impuremana.getFluid(18))
                .circuitMeta(1)
                .duration(100)
                .EUt(120)
                .buildAndRegister();
        //不纯魔力+铁粉 高炉烧制魔力钢锭
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Iron)
                .fluidInputs(PollutionMaterials.impuremana.getFluid(144))
                .output(OrePrefix.ingot, PollutionMaterials.manasteel)
                .blastFurnaceTemp(1800)
                .duration(400)
                .EUt(120)
                .buildAndRegister();
        //世界盐 搅拌机配方
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, PollutionMaterials.infused_air)
                .input(OrePrefix.dust, PollutionMaterials.infused_fire)
                .input(OrePrefix.dust, PollutionMaterials.infused_water)
                .input(OrePrefix.dust, PollutionMaterials.infused_earth)
                .input(OrePrefix.dust, PollutionMaterials.infused_order)
                .input(OrePrefix.dust, PollutionMaterials.infused_entropy)
                .fluidInputs(Redstone.getFluid(288))
                .output(OrePrefix.dust, PollutionMaterials.salismundus)
                .duration(600)
                .EUt(120)
                .buildAndRegister();
        //3魔力钢粉+2神秘粉+1世界盐粉=6漫宿钢粉
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, PollutionMaterials.manasteel, 3)
                .input(OrePrefix.dust, PollutionMaterials.thaumium, 2)
                .input(OrePrefix.dust, PollutionMaterials.salismundus)
                .output(OrePrefix.dust, PollutionMaterials.mansussteel)
                .duration(600)
                .EUt(120)
                .buildAndRegister();
        //这里是五个核心的配方
        //魔导强磁阵法核心
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "beam_core_0"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaBlocks.BEAM_CORE.getItemVariant(POMBeamCore.MagicBlockType.BEAM_CORE_0),
                6,
                new AspectList().add(Aspect.MECHANISM, 64).add(Aspect.MAGIC, 32).add(Aspect.METAL, 64),
                "blockSteelMagnetic",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "frameGtMansussteel",
                new ItemStack(ItemsTC.visResonator),
                new ItemStack(ItemsTC.morphicResonator)));
        //魔导内爆阵法核心
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "beam_core_1"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaBlocks.BEAM_CORE.getItemVariant(POMBeamCore.MagicBlockType.BEAM_CORE_1),
                6,
                new AspectList().add(Aspect.MECHANISM, 64).add(Aspect.MAGIC, 32).add(Aspect.ENTROPY, 64),
                new ItemStack(Blocks.TNT),
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "frameGtMansussteel",
                new ItemStack(ItemsTC.visResonator),
                new ItemStack(ItemsTC.morphicResonator)));
        //魔导导压阵法核心
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "beam_core_2"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaBlocks.BEAM_CORE.getItemVariant(POMBeamCore.MagicBlockType.BEAM_CORE_2),
                6,
                new AspectList().add(Aspect.MECHANISM, 64).add(Aspect.MAGIC, 32).add(Aspect.SENSES, 64),
                new ItemStack(Blocks.SOUL_SAND),
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "frameGtMansussteel",
                new ItemStack(ItemsTC.visResonator),
                new ItemStack(ItemsTC.morphicResonator)));
        //魔导束流阵法核心
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "beam_core_3"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaBlocks.BEAM_CORE.getItemVariant(POMBeamCore.MagicBlockType.BEAM_CORE_3),
                6,
                new AspectList().add(Aspect.MECHANISM, 64).add(Aspect.MAGIC, 32).add(Aspect.WATER, 64),
                new ItemStack(BlocksTC.crystalWater),
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "frameGtMansussteel",
                new ItemStack(ItemsTC.visResonator),
                new ItemStack(ItemsTC.morphicResonator)));
        //魔导聚能阵法核心
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "beam_core_4"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaBlocks.BEAM_CORE.getItemVariant(POMBeamCore.MagicBlockType.BEAM_CORE_4),
                6,
                new AspectList().add(Aspect.MECHANISM, 64).add(Aspect.MAGIC, 32).add(Aspect.ENERGY, 64),
                new ItemStack(BlocksTC.visBattery),
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "frameGtMansussteel",
                new ItemStack(ItemsTC.visResonator),
                new ItemStack(ItemsTC.morphicResonator)));
    }


}
