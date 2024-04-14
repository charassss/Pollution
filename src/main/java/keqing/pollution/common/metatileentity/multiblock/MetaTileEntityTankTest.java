package keqing.pollution.common.metatileentity.multiblock;

import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.RelativeDirection;
import gregtech.api.util.interpolate.Eases;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.IRenderSetup;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.shader.postprocessing.BloomEffect;
import gregtech.client.shader.postprocessing.BloomType;
import gregtech.client.utils.*;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockTurbineCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.pollution.api.metatileentity.POMultiblockAbility;
import keqing.pollution.api.metatileentity.PORecipeMapMultiblockController;
import keqing.pollution.api.metatileentity.POTankMultiblockController;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import thaumcraft.common.tiles.essentia.TileJarFillable;

public class MetaTileEntityTankTest extends POTankMultiblockController implements IBloomEffect , IFastRenderMetaTileEntity {

    int RadomTime=0;
    public MetaTileEntityTankTest(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[] { RecipeMaps.EXTRACTOR_RECIPES, RecipeMaps.COMPRESSOR_RECIPES,
                RecipeMaps.FORMING_PRESS_RECIPES, RecipeMaps.FORGE_HAMMER_RECIPES });
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityTankTest(this.metaTileEntityId);
    }

    @Override
    protected  BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXX","X###X", "X###X", "X###X", "#####", "#####")
                .aisle("XXXXX","#XXX#", "#XXX#", "#X#X#", "#X#X#", "#X#X#")
                .aisle("XXXXX","#XXX#", "#XXX#", "#####", "#####", "#####")
                .aisle("XXXXX","#XSX#", "#XFX#", "#X#X#", "#X#X#", "#X#X#")
                .aisle("XXXXX","X###X", "X###X", "X###X", "#####", "#####")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(25).or(autoAbilities()))
                .where('#', any())
                .where('F', abilities(POMultiblockAbility.TANK_HATCH).setMaxGlobalLimited(1).setPreviewCount(1))
                .build();
    }

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

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GCYMTextures.STRESS_PROOF_CASING;
    }

    @Override
    protected  OrientedOverlayRenderer getFrontOverlay() {
        return GCYMTextures.LARGE_BENDER_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }
    @Override
    public void update() {
        super.update();
        if(RadomTime<10)RadomTime++;
        else RadomTime=0;
        setFusionRingColor(0xFF000000+RadomTime*100);

    }







    protected static final int NO_COLOR = 0;
    private int fusionRingColor = NO_COLOR;
    private boolean registeredBloomRenderTicket;
    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }
    protected int getFusionRingColor() {
        return this.fusionRingColor;
    }
    protected boolean hasFusionRingColor() {
        return true;
    }
    protected void setFusionRingColor(int fusionRingColor) {
        if (this.fusionRingColor != fusionRingColor) {
            this.fusionRingColor = fusionRingColor;
            //writeCustomData(GregtechDataCodes.UPDATE_COLOR, buf -> buf.writeVarInt(fusionRingColor));
        }
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(double x, double y, double z, float partialTicks) {
        if (this.hasFusionRingColor() && !this.registeredBloomRenderTicket) {
            this.registeredBloomRenderTicket = true;
            BloomEffectUtil.registerBloomRender(FusionBloomSetup.INSTANCE, getBloomType(), this, this);
        }
    }
    private static BloomType getBloomType() {
        ConfigHolder.FusionBloom fusionBloom = ConfigHolder.client.shader.fusionBloom;
        return BloomType.fromValue(fusionBloom.useShader ? fusionBloom.bloomStyle : -1);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void renderBloomEffect(BufferBuilder buffer, EffectRenderContext context) {
        int color = RenderUtil.interpolateColor(this.getFusionRingColor(), -1, Eases.QUAD_IN.getInterpolation(
                Math.abs((Math.abs(getOffsetTimer() % 50) + context.partialTicks()) - 25) / 25));
        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        EnumFacing relativeBack = RelativeDirection.BACK.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());
        EnumFacing.Axis axis = RelativeDirection.UP.getRelativeFacing(getFrontFacing(), getUpwardsFacing(), isFlipped())
                .getAxis();

        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 1 + 0.5,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset() * 1 + 8,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 1 + 0.5,
                1, 1, 10, 20,
                r, g, b, a, axis);

        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 1 + 0.5,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset() * 1 + 8,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 1 + 0.5,
                1, 1, 10, 20,
                r, g, b, a, EnumFacing.Axis.X);

        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 1 + 0.5,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset() * 1 + 8,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 1 + 0.5,
                1, 1, 10, 20,
                r, g, b, a, EnumFacing.Axis.Z);


        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 1 + 0.5,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset() * 1 + 8,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 1 + 0.5,
                RadomTime, 0.2, 10, 20,
                r, g, b, a, EnumFacing.Axis.Y);

    }
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRenderBloomEffect( EffectRenderContext context) {
        return this.hasFusionRingColor();
    }
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        EnumFacing relativeRight = RelativeDirection.RIGHT.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());
        EnumFacing relativeBack = RelativeDirection.BACK.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());

        return new AxisAlignedBB(
                this.getPos().offset(relativeBack).offset(relativeRight, 6),
                this.getPos().offset(relativeBack, 13).offset(relativeRight.getOpposite(), 6));
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 0;
    }

    @Override
    public boolean isGlobalRenderer() {
        return true;
    }
    @SideOnly(Side.CLIENT)
    private static final class FusionBloomSetup implements IRenderSetup {

        private static final FusionBloomSetup INSTANCE = new FusionBloomSetup();

        float lastBrightnessX;
        float lastBrightnessY;

        @Override
        public void preDraw( BufferBuilder buffer) {
            BloomEffect.strength = (float) ConfigHolder.client.shader.fusionBloom.strength;
            BloomEffect.baseBrightness = (float) ConfigHolder.client.shader.fusionBloom.baseBrightness;
            BloomEffect.highBrightnessThreshold = (float) ConfigHolder.client.shader.fusionBloom.highBrightnessThreshold;
            BloomEffect.lowBrightnessThreshold = (float) ConfigHolder.client.shader.fusionBloom.lowBrightnessThreshold;
            BloomEffect.step = 1;

            lastBrightnessX = OpenGlHelper.lastBrightnessX;
            lastBrightnessY = OpenGlHelper.lastBrightnessY;
            GlStateManager.color(1, 1, 1, 1);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GlStateManager.disableTexture2D();

            buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
        }

        @Override
        public void postDraw( BufferBuilder buffer) {
            Tessellator.getInstance().draw();

            GlStateManager.enableTexture2D();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
        }
    }
}