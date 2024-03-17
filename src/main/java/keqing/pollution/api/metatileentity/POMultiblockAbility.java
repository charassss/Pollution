package keqing.pollution.api.metatileentity;

import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import keqing.pollution.api.capability.ITankHatch;
import keqing.pollution.api.capability.IVisHatch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.IAspectContainer;

import java.util.List;

@SuppressWarnings("InstantiationOfUtilityClass")
public final class POMultiblockAbility {
    public static final MultiblockAbility<ITankHatch> TANK_HATCH = new MultiblockAbility<>("tank_hatch");
    public static final MultiblockAbility<IVisHatch> VIS_HATCH = new MultiblockAbility<>("vis_hatch");
    private POMultiblockAbility() {}

}