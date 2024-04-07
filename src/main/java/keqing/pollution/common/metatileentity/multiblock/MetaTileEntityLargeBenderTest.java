package keqing.pollution.common.metatileentity.multiblock;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockTurbineCasing;
import gregtech.common.blocks.MetaBlocks;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import keqing.pollution.api.metatileentity.POMultiblockAbility;
import keqing.pollution.api.metatileentity.PORecipeMapMultiblockController;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import static keqing.pollution.api.unification.PollutionMaterials.infused_air;

public class MetaTileEntityLargeBenderTest extends PORecipeMapMultiblockController {

    //我是神秘GCYM多方块 看我看我

    //这里是多方块的配方  RecipeMaps.BENDER_RECIPES, RecipeMaps.COMPRESSOR_RECIPES,
    //                RecipeMaps.FORMING_PRESS_RECIPES, RecipeMaps.FORGE_HAMMER_RECIPES 都是配方喵
    //你可以只写一种配方 也可以写很多喵
    public MetaTileEntityLargeBenderTest(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[] { RecipeMaps.BENDER_RECIPES, RecipeMaps.COMPRESSOR_RECIPES,
                RecipeMaps.FORMING_PRESS_RECIPES, RecipeMaps.FORGE_HAMMER_RECIPES });
    }


    //不要动
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityLargeBenderTest(this.metaTileEntityId);
    }

    //这里是多方块的结构喵
    //细节
    //                .aisle("XXXXXXX", "XXXXXXX", "XXXXXXX")
    //                .aisle("XXXXXXX", "XXXGGGX", "XXXXXXX")
    //                .aisle("IFXXXXX", "XSXCCCX", "XXXXXXX")
    //这个是结构喵
    // .where('S', selfPredicate()) 必写 控制器位置
    // .where('X', states(getCasingState()).setMinGlobalLimited(40).or(autoAbilities()))
    //绑定如下方法 指定多方块此位置的方块  .setMinGlobalLimited(40)表示最小数量  .or(autoAbilities()))自动IO
    // private static IBlockState getCasingState() {
    //        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING
    //                .getState(BlockLargeMultiblockCasing.CasingType.STRESS_PROOF_CASING);
    //    }


    //                .where('I', abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(1).setPreviewCount(1))
    //                .where('F', abilities(POMultiblockAbility.VIS_HATCH).setMaxGlobalLimited(1).setPreviewCount(1))
    //手动指定仓口  例如灵气仓 VIS_HATCH


    @Override
    protected  BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXXXX", "XXXXXXX", "XXXXXXX")
                .aisle("XXXXXXX", "XXXGGGX", "XXXXXXX")
                .aisle("IFXXXXX", "XSXCCCX", "XXXXXXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(40).or(autoAbilities()))
                .where('G', states(getCasingState2()))
                .where('C', states(getCasingState3()))
                .where('I', abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(1).setPreviewCount(1))
                .where('F', abilities(POMultiblockAbility.VIS_HATCH).setMaxGlobalLimited(1).setPreviewCount(1))
                .build();
    }

    //这里是多方块工作需要消耗的 元素
    @Override
    public Material getMaterial()
    {
        return infused_air;
    }



    //下边都是设置多方块外形材质的喵
    private static IBlockState getCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING
                .getState(BlockLargeMultiblockCasing.CasingType.STRESS_PROOF_CASING);
    }

    private static IBlockState getCasingState2() {
        return MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STEEL_GEARBOX);
    }

    private static IBlockState getCasingState3() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }


    //覆盖层材质 就是给IO渲染的材质
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GCYMTextures.STRESS_PROOF_CASING;
    }


    //控制器的图形 比如传统的外观 或者聚变电脑的外观等等
    @Override
    protected  OrientedOverlayRenderer getFrontOverlay() {
        return GCYMTextures.LARGE_BENDER_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }
}