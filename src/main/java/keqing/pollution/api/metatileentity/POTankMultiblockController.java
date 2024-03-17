package keqing.pollution.api.metatileentity;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTTransferUtils;
import keqing.pollution.api.capability.IVisMultiblock;
import keqing.pollution.api.unification.PollutionMaterials;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.tiles.essentia.TileJarFillable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static keqing.pollution.api.unification.PollutionMaterials.*;

public abstract class POTankMultiblockController extends MultiMapMultiblockController {

    int aX = 0;
    int aY = 0;
    int aZ = 0;
    public Aspect al;
    public String name=null;
    public int storage=0;
    public int number=0;
    public POTankMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMaps) {
        super(metaTileEntityId, recipeMaps);
    }

    public boolean fillTanks(FluidStack stack, boolean simulate) {
        return GTTransferUtils.addFluidsToFluidHandler(getOutputFluidInventory(), simulate, Collections.singletonList(stack));
    }

    @Override
    public void update() {
        super.update();
        if(getWorld().getTileEntity(getPos().add(0,2,0)) instanceof TileJarFillable)
        {
            var s  = (TileJarFillable)getWorld().getTileEntity(getPos().add(0,2,0));
                al=s.getEssentiaType(s.getFacing());
                storage=s.getEssentiaAmount(s.getFacing());
                if(al!=null)name=al.getName();
        }
        if(number==0)
        {
            number=storage;
            clearInfused();
            name=null;
        }
        doFillTank();
    }
    FluidStack AIR_STACK = infused_air.getFluid(1);
    FluidStack FIRE_STACK = infused_fire.getFluid(1);
    FluidStack WATER_STACK = infused_water.getFluid(1);
    FluidStack ERATH_STACK = infused_earth.getFluid(1);
    FluidStack ORDER_STACK = infused_order.getFluid(1);
    FluidStack ENTROPY_STACK = infused_entropy.getFluid(1);
    private void doFillTank() {
        if(Objects.equals(name, "Aer") &&number>0)
        {
            fillTanks(AIR_STACK,false);
            number--;
        }
        if(Objects.equals(name, "Terra") &&number>0)
        {
            fillTanks(ERATH_STACK,false);
            number--;
        }
        if(Objects.equals(name, "Aqua") &&number>0)
        {
            fillTanks(WATER_STACK,false);
            number--;
        }
        if(Objects.equals(name, "Ignis") &&number>0)
        {
            fillTanks(FIRE_STACK,false);
            number--;
        }
        if(Objects.equals(name, "Ordo") &&number>0)
        {
            fillTanks(ORDER_STACK,false);
            number--;
        }
        if(Objects.equals(name, "Perdition") &&number>0)
        {
            fillTanks(ENTROPY_STACK,false);
            number--;
        }
    }

    public  void clearInfused()
    {
        if(getWorld().getTileEntity(getPos().add(0,2,0)) instanceof TileJarFillable)
        {
            var s  = (TileJarFillable)getWorld().getTileEntity(getPos().add(0,2,0));
            s.amount=0;
        }
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        //aX=this.getAbilities(POMultiblockAbility.TANK_HATCH).get(0).getX();
        //aY=this.getAbilities(POMultiblockAbility.TANK_HATCH).get(0).getY();
        //aZ=this.getAbilities(POMultiblockAbility.TANK_HATCH).get(0).getZ();

    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed())
        textList.add(new TextComponentTranslation("Infused: %s  Amount: %s",name,storage));
        textList.add(new TextComponentTranslation("Infused: %s  Amount: %s",name,number));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("number", this.number);
        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.number = data.getInteger("number");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.number);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.number = buf.readInt();
    }

}