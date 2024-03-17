package keqing.pollution.common.metatileentity.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import keqing.pollution.api.capability.ITankHatch;
import keqing.pollution.api.capability.IVisHatch;
import keqing.pollution.api.metatileentity.POMultiblockAbility;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.EmptyChunk;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftInvHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.tiles.essentia.TileJar;
import thaumcraft.common.tiles.essentia.TileJarFillable;

import java.util.List;

public class MetaTileEntityTankHatch extends MetaTileEntityMultiblockPart
    implements IMultiblockAbilityPart<ITankHatch>,ITankHatch{

    int tier;
    public Aspect al=null;
    public int storage=0;
    String name=null;
    int aX = 0;
    int aY = 0;
    int aZ = 0;
    @Override
    public void update() {
        super.update();
        //当前位置
        aX = this.getPos().getX();
        aY = this.getPos().getY();
        aZ = this.getPos().getZ();
        //找罐子
        if(getWorld().getTileEntity(getPos().add(0,1,0)) instanceof TileJarFillable)
        {
            var s  = (TileJarFillable)getWorld().getTileEntity(getPos().add(0,1,0));
            al=s.getEssentiaType(s.getFacing());
            storage=s.getEssentiaAmount(s.getFacing());
            if(al!=null)name=al.getName();
        }
    }
    public MetaTileEntityTankHatch(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.tier=tier;
    }



    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityTankHatch(this.metaTileEntityId, this.getTier());
    }
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
    }
    @Override
    public int getTier() {
        return tier;
    }
    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.defaultBuilder();
        builder.dynamicLabel(7, 30, () -> "Tank Hatch", 0x232323);
        builder.dynamicLabel(7, 50, () -> "Tier: " + this.getTier(), 0x232323);
        builder.dynamicLabel(7, 70, () -> "locate: " + this.aX+" / "+this.aY+" / "+this.aZ, 0x232323);
        builder.dynamicLabel(7, 110, () -> "Infused: " + name+" Amount: "+storage, 0x232323);
        return builder.build(getHolder(), entityPlayer);
    }
    @Override
    public MultiblockAbility<ITankHatch> getAbility() {
        return POMultiblockAbility.TANK_HATCH;
    }

    @Override
    public void registerAbilities(List<ITankHatch> list) {
        list.add(this);
    }


    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (shouldRenderOverlay()) {
            OrientedOverlayRenderer overlayRenderer;
            if (getTier() <= GTValues.HV)
                overlayRenderer = GCYMTextures.PARALLEL_HATCH_MK1_OVERLAY;
            else if (getTier() <= GTValues.ZPM)
                overlayRenderer = GCYMTextures.PARALLEL_HATCH_MK2_OVERLAY;
            else if (getTier() <= GTValues.UEV)
                overlayRenderer = GCYMTextures.PARALLEL_HATCH_MK3_OVERLAY;
            else
                overlayRenderer = GCYMTextures.PARALLEL_HATCH_MK4_OVERLAY;

            if (getController() != null && getController() instanceof RecipeMapMultiblockController) {
                overlayRenderer.renderOrientedState(renderState, translation, pipeline, getFrontFacing(),
                        getController().isActive(),
                        getController().getCapability(GregtechTileCapabilities.CAPABILITY_CONTROLLABLE, null)
                                .isWorkingEnabled());
            } else {
                overlayRenderer.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), false, false);
            }
        }
    }
    @Override
    public boolean canPartShare() {
        return false;
    }
    //ITankHatch
    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getZ() {
        return 0;
    }
}
