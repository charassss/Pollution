package keqing.pollution.loaders.recipes;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.common.blocks.*;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.pollution.Pollution;
import keqing.pollution.api.unification.PollutionMaterials;
import keqing.pollution.common.block.PollutionMetaBlock.*;
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
                .duration(300)
                .EUt(120)
                .buildAndRegister();
        //火要素-残日钢
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Steel,  2)
                .input(OrePrefix.dust, Magnesium, 1)
                .input(OrePrefix.dust, Lithium, 1)
                .input(OrePrefix.dust, PollutionMaterials.infused_fire, 5)
                .output(OrePrefix.dust, PollutionMaterials.ignissteel, 9)
                .duration(300)
                .EUt(120)
                .buildAndRegister();
        //水要素-捩花银
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Silver,  2)
                .input(OrePrefix.dust, Tin, 1)
                .fluidInputs(Mercury.getFluid(1000))
                .input(OrePrefix.dust, PollutionMaterials.infused_water, 5)
                .output(OrePrefix.dust, PollutionMaterials.aquasilver, 9)
                .duration(300)
                .EUt(120)
                .buildAndRegister();
        //地要素-定坤铜
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Copper,  2)
                .input(OrePrefix.dust, Boron, 1)
                .input(OrePrefix.dust, Carbon, 1)
                .input(OrePrefix.dust, PollutionMaterials.infused_earth, 5)
                .output(OrePrefix.dust, PollutionMaterials.terracopper, 9)
                .duration(300)
                .EUt(120)
                .buildAndRegister();
        //秩序要素-司辰铅
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Lead,  2)
                .input(OrePrefix.dust, Silicon, 1)
                .input(OrePrefix.dust, Gold, 1)
                .input(OrePrefix.dust, PollutionMaterials.infused_order, 5)
                .output(OrePrefix.dust, PollutionMaterials.ordolead, 9)
                .duration(300)
                .EUt(120)
                .buildAndRegister();
        //混沌要素-无极铝
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Aluminium,  2)
                .fluidInputs(Fluorine.getFluid(1000))
                .input(OrePrefix.dust, Thorium, 1)
                .input(OrePrefix.dust, PollutionMaterials.infused_entropy, 5)
                .output(OrePrefix.dust, PollutionMaterials.perditioaluminium, 9)
                .duration(300)
                .EUt(120)
                .buildAndRegister();
        //高炉2700°烧六种合金捏
        //这里是六个外壳的制作配方
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, PollutionMaterials.aertitanium, 6)
                .input(OrePrefix.frameGt, PollutionMaterials.aertitanium, 1)
                .outputs(PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_AIR))
                .circuitMeta(6)
                .duration(300)
                .EUt(120)
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, PollutionMaterials.ignissteel, 6)
                .input(OrePrefix.frameGt, PollutionMaterials.ignissteel, 1)
                .outputs(PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_HOT))
                .circuitMeta(6)
                .duration(300)
                .EUt(120)
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, PollutionMaterials.aquasilver, 6)
                .input(OrePrefix.frameGt, PollutionMaterials.aquasilver, 1)
                .outputs(PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_WATER))
                .circuitMeta(6)
                .duration(300)
                .EUt(120)
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, PollutionMaterials.terracopper, 6)
                .input(OrePrefix.frameGt, PollutionMaterials.terracopper, 1)
                .outputs(PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_EARTH))
                .circuitMeta(6)
                .duration(300)
                .EUt(120)
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, PollutionMaterials.ordolead, 6)
                .input(OrePrefix.frameGt, PollutionMaterials.ordolead, 1)
                .outputs(PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_ORDER))
                .circuitMeta(6)
                .duration(300)
                .EUt(120)
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, PollutionMaterials.perditioaluminium, 6)
                .input(OrePrefix.frameGt, PollutionMaterials.perditioaluminium, 1)
                .outputs(PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.VOID_PRISM))
                .circuitMeta(6)
                .duration(300)
                .EUt(120)
                .buildAndRegister();
        //这里是主方块的注魔配方
        //配方暂定造价：主注魔材料催化剂，次要材料mv电路板*4+对应要素的水晶+mv级别的对应小机器一台+一个对应外壳+法罗钠晶体一个
        //卷板
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_bender"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaTileEntities.MAGIC_BENDER.getStackForm(),
                5,
                new AspectList().add(Aspect.METAL, 250).add(Aspect.MAGIC, 64).add(Aspect.MECHANISM, 128),
                new ItemStack(PollutionMetaItems.INTEGRATECORE.getMetaItem(), 1, 5),
                "gemValonite",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "oreCrystalOrder",
                "oreCrystalEarth",
                MetaTileEntities.BENDER[MV].getStackForm(),
                MetaTileEntities.COMPRESSOR[MV].getStackForm(),
                MetaTileEntities.FORMING_PRESS[MV].getStackForm(),
                MetaTileEntities.FORGE_HAMMER[MV].getStackForm(),
                PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_ORDER)));
        //固化三合一
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_solidifier"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaTileEntities.MAGIC_SOLIDIFIER.getStackForm(),
                5,
                new AspectList().add(Aspect.EXCHANGE, 250).add(Aspect.MAGIC, 64).add(Aspect.MECHANISM, 128),
                new ItemStack(PollutionMetaItems.COLDCORE.getMetaItem(), 1, 4),
                new ItemStack(PollutionMetaItems.SEGREGATECORE.getMetaItem(), 1, 6),
                new ItemStack(PollutionMetaItems.INTEGRATECORE.getMetaItem(), 1, 5),
                "gemValonite",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "oreCrystalOrder",
                "oreCrystalEntropy",
                MetaTileEntities.FLUID_SOLIDIFIER[MV].getStackForm(),
                MetaTileEntities.CANNER[MV].getStackForm(),
                MetaTileEntities.EXTRACTOR[MV].getStackForm(),
                PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_VOID),
                PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_ORDER)));
        //轧线
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_wiremill"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaTileEntities.MAGIC_WIREMILL.getStackForm(),
                5,
                new AspectList().add(Aspect.TOOL, 250).add(Aspect.MAGIC, 64).add(Aspect.MECHANISM, 128),
                new ItemStack(PollutionMetaItems.SEGREGATECORE.getMetaItem(), 1, 6),
                "gemValonite",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "oreCrystalOrder",
                "oreCrystalEarth",
                "oreCrystalFire",
                MetaTileEntities.WIREMILL[MV].getStackForm(),
                PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_ORDER)));
        //筛选
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_sifter"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaTileEntities.MAGIC_SIFTER.getStackForm(),
                5,
                new AspectList().add(Aspect.CRYSTAL, 250).add(Aspect.MAGIC, 64).add(Aspect.MECHANISM, 128),
                new ItemStack(PollutionMetaItems.INTEGRATECORE.getMetaItem(), 1, 5),
                "gemValonite",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "oreCrystalEarth",
                "oreCrystalAir",
                MetaTileEntities.SIFTER[MV].getStackForm(),
                PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_EARTH),
                PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_AIR)));
        //切割
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_cutter"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaTileEntities.MAGIC_CUTTER.getStackForm(),
                5,
                new AspectList().add(Aspect.SOUL, 125).add(Aspect.ENTROPY, 125).add(Aspect.MAGIC, 64).add(Aspect.MECHANISM, 128),
                new ItemStack(PollutionMetaItems.HOTCORE.getMetaItem(), 1, 3),
                "gemValonite",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "circuitMv",
                "oreCrystalEntropy",
                "oreCrystalEarth",
                "oreCrystalWater",
                MetaTileEntities.CUTTER[MV].getStackForm(),
                PollutionMetaBlocks.MAGIC_BLOCK.getItemVariant(POMagicBlock.MagicBlockType.SPELL_PRISM_HOT)));
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
        //线圈（八个）
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_wirecoil-1"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaBlocks.WIRE_COIL.getItemVariant(POCoilBlock.WireCoilType.COIL_LEVEL_1),
                4,
                new AspectList().add(Aspect.FIRE, 4).add(Aspect.MAGIC, 4),
                MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.CUPRONICKEL),
                "plateMansussteel",
                "plateMansussteel",
                "plateIgnissteel",
                "plateIgnissteel",
                new ItemStack(ItemsTC.visResonator)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_wirecoil-2"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaBlocks.WIRE_COIL.getItemVariant(POCoilBlock.WireCoilType.COIL_LEVEL_2),
                4,
                new AspectList().add(Aspect.FIRE, 4).add(Aspect.MAGIC, 4),
                MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.KANTHAL),
                "plateMansussteel",
                "plateMansussteel",
                "plateIgnissteel",
                "plateIgnissteel",
                new ItemStack(ItemsTC.visResonator)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_wirecoil-3"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaBlocks.WIRE_COIL.getItemVariant(POCoilBlock.WireCoilType.COIL_LEVEL_3),
                4,
                new AspectList().add(Aspect.FIRE, 8).add(Aspect.MAGIC, 8),
                MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.NICHROME),
                "plateMansussteel",
                "plateMansussteel",
                "plateIgnissteel",
                "plateIgnissteel",
                new ItemStack(ItemsTC.morphicResonator),
                new ItemStack(ItemsTC.visResonator)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_wirecoil-4"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaBlocks.WIRE_COIL.getItemVariant(POCoilBlock.WireCoilType.COIL_LEVEL_4),
                4,
                new AspectList().add(Aspect.FIRE, 8).add(Aspect.MAGIC, 8),
                MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.RTM_ALLOY),
                "plateMansussteel",
                "plateMansussteel",
                "plateIgnissteel",
                "plateIgnissteel",
                new ItemStack(ItemsTC.morphicResonator),
                new ItemStack(ItemsTC.visResonator)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_wirecoil-5"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaBlocks.WIRE_COIL.getItemVariant(POCoilBlock.WireCoilType.COIL_LEVEL_5),
                4,
                new AspectList().add(Aspect.FIRE, 16).add(Aspect.MAGIC, 16),
                MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.HSS_G),
                "plateMansussteel",
                "plateMansussteel",
                "plateIgnissteel",
                "plateIgnissteel",
                "blockSubstrate",
                new ItemStack(ItemsTC.morphicResonator),
                new ItemStack(ItemsTC.visResonator)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_wirecoil-6"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaBlocks.WIRE_COIL.getItemVariant(POCoilBlock.WireCoilType.COIL_LEVEL_6),
                4,
                new AspectList().add(Aspect.FIRE, 16).add(Aspect.MAGIC, 16),
                MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.NAQUADAH),
                "plateMansussteel",
                "plateMansussteel",
                "plateIgnissteel",
                "plateIgnissteel",
                "blockSubstrate",
                new ItemStack(ItemsTC.morphicResonator),
                new ItemStack(ItemsTC.visResonator)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_wirecoil-7"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaBlocks.WIRE_COIL.getItemVariant(POCoilBlock.WireCoilType.COIL_LEVEL_7),
                4,
                new AspectList().add(Aspect.FIRE, 32).add(Aspect.MAGIC, 32),
                MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.TRINIUM),
                "plateMansussteel",
                "plateMansussteel",
                "plateIgnissteel",
                "plateIgnissteel",
                "blockSubstrate",
                new ItemStack(BlocksTC.visBattery),
                new ItemStack(ItemsTC.morphicResonator),
                new ItemStack(ItemsTC.visResonator)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "magic_wirecoil-8"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaBlocks.WIRE_COIL.getItemVariant(POCoilBlock.WireCoilType.COIL_LEVEL_8),
                4,
                new AspectList().add(Aspect.FIRE, 32).add(Aspect.MAGIC, 32),
                MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.TRITANIUM),
                "plateMansussteel",
                "plateMansussteel",
                "plateIgnissteel",
                "plateIgnissteel",
                "blockSubstrate",
                new ItemStack(BlocksTC.visBattery),
                new ItemStack(ItemsTC.morphicResonator),
                new ItemStack(ItemsTC.visResonator)));

        //玻璃
        //a型玻璃
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Thaumcraft.MODID, "glass-a"), new ShapedArcaneRecipe(
                new ResourceLocation(""),
                "FIRSTSTEPS@2",
                25,
                new AspectList().add(Aspect.EARTH, 1).add(Aspect.AIR, 1),
                PollutionMetaBlocks.GLASS.getItemVariant(POGlass.MagicBlockType.AAMINATED_GLASS),
                "ADA",
                "BCB",
                "ADA",
                'A', "blockGlass",
                'B', new ItemStack(ItemsTC.visResonator),
                'C', new ItemStack(ItemsTC.morphicResonator),
                'D', "plateMansussteel"));
        //灵气仓（只到iv）
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "vis_hatch-1"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaTileEntities.VIS_HATCH[ULV].getStackForm(),
                6,
                new AspectList().add(Aspect.AURA, 128).add(Aspect.MAGIC, 32).add(Aspect.DESIRE, 64),
                MetaTileEntities.FLUID_IMPORT_HATCH[LV].getStackForm(),
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                new ItemStack(MetaItems.FIELD_GENERATOR_LV.getMetaItem(),1, 202), //lv从202开始
                new ItemStack(ItemsTC.visResonator),
                new ItemStack(ItemsTC.morphicResonator)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "vis_hatch-2"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaTileEntities.VIS_HATCH[LV].getStackForm(),
                6,
                new AspectList().add(Aspect.AURA, 128).add(Aspect.MAGIC, 32).add(Aspect.DESIRE, 64),
                MetaTileEntities.FLUID_IMPORT_HATCH[MV].getStackForm(),
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                new ItemStack(MetaItems.FIELD_GENERATOR_MV.getMetaItem(),1, 203),
                new ItemStack(ItemsTC.visResonator),
                new ItemStack(ItemsTC.morphicResonator)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "vis_hatch-3"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaTileEntities.VIS_HATCH[MV].getStackForm(),
                6,
                new AspectList().add(Aspect.AURA, 128).add(Aspect.MAGIC, 32).add(Aspect.DESIRE, 64),
                MetaTileEntities.FLUID_IMPORT_HATCH[HV].getStackForm(),
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                new ItemStack(MetaItems.FIELD_GENERATOR_HV.getMetaItem(),1, 204),
                new ItemStack(ItemsTC.visResonator),
                new ItemStack(ItemsTC.morphicResonator)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "vis_hatch-4"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaTileEntities.VIS_HATCH[HV].getStackForm(),
                6,
                new AspectList().add(Aspect.AURA, 128).add(Aspect.MAGIC, 32).add(Aspect.DESIRE, 64),
                MetaTileEntities.FLUID_IMPORT_HATCH[EV].getStackForm(),
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                new ItemStack(MetaItems.FIELD_GENERATOR_EV.getMetaItem(),1, 205),
                new ItemStack(ItemsTC.visResonator),
                new ItemStack(ItemsTC.morphicResonator)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Pollution.MODID, "vis_hatch-5"), new InfusionRecipe(
                "INFUSION@2",
                PollutionMetaTileEntities.VIS_HATCH[EV].getStackForm(),
                6,
                new AspectList().add(Aspect.AURA, 128).add(Aspect.MAGIC, 32).add(Aspect.DESIRE, 64),
                MetaTileEntities.FLUID_IMPORT_HATCH[IV].getStackForm(),
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                "plateMansussteel",
                new ItemStack(MetaItems.FIELD_GENERATOR_IV.getMetaItem(),1, 206),
                new ItemStack(ItemsTC.visResonator),
                new ItemStack(ItemsTC.morphicResonator)));
        //管道
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Thaumcraft.MODID, "pipe-steel"), new ShapedArcaneRecipe(
                new ResourceLocation(""),
                "FIRSTSTEPS@2",
                25,
                new AspectList().add(Aspect.EARTH, 1).add(Aspect.ORDER, 1),
                PollutionMetaBlocks.TURBINE.getItemVariant(POTurbine.MagicBlockType.STEEL_PIPE),
                "BBB",
                "ACA",
                "BBB",
                'A', "plateMansussteel",
                'B', "plateSteel",
                'C', MetaBlocks.BOILER_CASING.getItemVariant(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Thaumcraft.MODID, "pipe-stainless"), new ShapedArcaneRecipe(
                new ResourceLocation(""),
                "FIRSTSTEPS@2",
                25,
                new AspectList().add(Aspect.EARTH, 1).add(Aspect.ORDER, 1),
                PollutionMetaBlocks.TURBINE.getItemVariant(POTurbine.MagicBlockType.STEEL_PIPE),
                "BBB",
                "ACA",
                "BBB",
                'A', "plateMansussteel",
                'B', "plateStainlessSteel",
                'C', MetaBlocks.BOILER_CASING.getItemVariant(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Thaumcraft.MODID, "pipe-polytetrafluoroethylene"), new ShapedArcaneRecipe(
                new ResourceLocation(""),
                "FIRSTSTEPS@2",
                25,
                new AspectList().add(Aspect.EARTH, 1).add(Aspect.ORDER, 1),
                PollutionMetaBlocks.TURBINE.getItemVariant(POTurbine.MagicBlockType.POLYTETRAFLUOROETHYLENE_PIPE),
                "BBB",
                "ACA",
                "BBB",
                'A', "plateMansussteel",
                'B', "platePolytetrafluoroethylene",
                'C', MetaBlocks.BOILER_CASING.getItemVariant(BlockBoilerCasing.BoilerCasingType.POLYTETRAFLUOROETHYLENE_PIPE)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Thaumcraft.MODID, "pipe-titanium"), new ShapedArcaneRecipe(
                new ResourceLocation(""),
                "FIRSTSTEPS@2",
                25,
                new AspectList().add(Aspect.EARTH, 1).add(Aspect.ORDER, 1),
                PollutionMetaBlocks.TURBINE.getItemVariant(POTurbine.MagicBlockType.TITANIUM_PIPE),
                "BBB",
                "ACA",
                "BBB",
                'A', "plateMansussteel",
                'B', "plateTitanium",
                'C', MetaBlocks.BOILER_CASING.getItemVariant(BlockBoilerCasing.BoilerCasingType.TITANIUM_PIPE)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Thaumcraft.MODID, "pipe-tungstensteel"), new ShapedArcaneRecipe(
                new ResourceLocation(""),
                "FIRSTSTEPS@2",
                25,
                new AspectList().add(Aspect.EARTH, 1).add(Aspect.ORDER, 1),
                PollutionMetaBlocks.TURBINE.getItemVariant(POTurbine.MagicBlockType.TUNGSTENSTEEL_PIPE),
                "BBB",
                "ACA",
                "BBB",
                'A', "plateMansussteel",
                'B', "plateTungstenSteel",
                'C', MetaBlocks.BOILER_CASING.getItemVariant(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE)));
        //齿轮
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Thaumcraft.MODID, "gearbox_steel"), new ShapedArcaneRecipe(
                new ResourceLocation(""),
                "FIRSTSTEPS@2",
                25,
                new AspectList().add(Aspect.EARTH, 1).add(Aspect.ORDER, 1),
                PollutionMetaBlocks.TURBINE.getItemVariant(POTurbine.MagicBlockType.STEEL_GEARBOX),
                "BBB",
                "ACA",
                "BBB",
                'A', "plateMansussteel",
                'B', "plateSteel",
                'C', MetaBlocks.TURBINE_CASING.getItemVariant(BlockTurbineCasing.TurbineCasingType.STEEL_GEARBOX)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Thaumcraft.MODID, "gearbox_bronze"), new ShapedArcaneRecipe(
                new ResourceLocation(""),
                "FIRSTSTEPS@2",
                25,
                new AspectList().add(Aspect.EARTH, 1).add(Aspect.ORDER, 1),
                PollutionMetaBlocks.TURBINE.getItemVariant(POTurbine.MagicBlockType.BRONZE_GEARBOX),
                "BBB",
                "ACA",
                "BBB",
                'A', "plateMansussteel",
                'B', "plateBronze",
                'C', MetaBlocks.TURBINE_CASING.getItemVariant(BlockTurbineCasing.TurbineCasingType.BRONZE_GEARBOX)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Thaumcraft.MODID, "gearbox_stainless"), new ShapedArcaneRecipe(
                new ResourceLocation(""),
                "FIRSTSTEPS@2",
                25,
                new AspectList().add(Aspect.EARTH, 1).add(Aspect.ORDER, 1),
                PollutionMetaBlocks.TURBINE.getItemVariant(POTurbine.MagicBlockType.STAINLESS_STEEL_GEARBOX),
                "BBB",
                "ACA",
                "BBB",
                'A', "plateMansussteel",
                'B', "plateStainlessSteel",
                'C', MetaBlocks.TURBINE_CASING.getItemVariant(BlockTurbineCasing.TurbineCasingType.STEEL_GEARBOX)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Thaumcraft.MODID, "gearbox_titanium"), new ShapedArcaneRecipe(
                new ResourceLocation(""),
                "FIRSTSTEPS@2",
                25,
                new AspectList().add(Aspect.EARTH, 1).add(Aspect.ORDER, 1),
                PollutionMetaBlocks.TURBINE.getItemVariant(POTurbine.MagicBlockType.TITANIUM_GEARBOX),
                "BBB",
                "ACA",
                "BBB",
                'A', "plateMansussteel",
                'B', "plateTitanium",
                'C', MetaBlocks.TURBINE_CASING.getItemVariant(BlockTurbineCasing.TurbineCasingType.TITANIUM_GEARBOX)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Thaumcraft.MODID, "gearbox_tungstensteel"), new ShapedArcaneRecipe(
                new ResourceLocation(""),
                "FIRSTSTEPS@2",
                25,
                new AspectList().add(Aspect.EARTH, 1).add(Aspect.ORDER, 1),
                PollutionMetaBlocks.TURBINE.getItemVariant(POTurbine.MagicBlockType.TUNGSTENSTEEL_GEARBOX),
                "BBB",
                "ACA",
                "BBB",
                'A', "plateMansussteel",
                'B', "plateTungstenSteel",
                'C', MetaBlocks.TURBINE_CASING.getItemVariant(BlockTurbineCasing.TurbineCasingType.TUNGSTENSTEEL_GEARBOX)));
    }


}
