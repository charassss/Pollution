package keqing.pollution.api.metatileentity;

import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTTransferUtils;
import keqing.pollution.api.unification.PollutionMaterials;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.tiles.essentia.TileJarFillable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class POTankMultiblockController extends MultiMapMultiblockController {
    int aX = 0;
    int aY = 0;
    int aZ = 0;
    public Aspect al;
    public String name = null;
    public int storage = 0;
    public int number = 0;
    FluidStack AIR_STACK;
    FluidStack FIRE_STACK;
    FluidStack WATER_STACK;
    FluidStack ERATH_STACK;
    FluidStack ORDER_STACK;
    FluidStack ENTROPY_STACK;

    public POTankMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMaps) {
        super(metaTileEntityId, recipeMaps);
        this.AIR_STACK = PollutionMaterials.infused_air.getFluid(1);
        this.FIRE_STACK = PollutionMaterials.infused_fire.getFluid(1);
        this.WATER_STACK = PollutionMaterials.infused_water.getFluid(1);
        this.ERATH_STACK = PollutionMaterials.infused_earth.getFluid(1);
        this.ORDER_STACK = PollutionMaterials.infused_order.getFluid(1);
        this.ENTROPY_STACK = PollutionMaterials.infused_entropy.getFluid(1);
    }

    public boolean fillTanks(FluidStack stack, boolean simulate) {
        return GTTransferUtils.addFluidsToFluidHandler(this.getOutputFluidInventory(), simulate, Collections.singletonList(stack));
    }

    public void update() {
        super.update();
        if (this.getWorld().getTileEntity(this.getPos().add(0, 2, 0)) instanceof TileJarFillable) {
            TileJarFillable s = (TileJarFillable)this.getWorld().getTileEntity(this.getPos().add(0, 2, 0));
            this.al = s.getEssentiaType(s.getFacing());
            this.storage = s.getEssentiaAmount(s.getFacing());
            if (this.al != null) {
                this.name = this.al.getName();
            }
        }

        if (this.number == 0) {
            this.number = this.storage;
            this.clearInfused();
            this.name = null;
        }

        this.doFillTank();
    }

    private void doFillTank() {
        if (Objects.equals(this.name, "Aer") && this.number > 0) {
            this.fillTanks(this.AIR_STACK, false);
            --this.number;
        }

        if (Objects.equals(this.name, "Terra") && this.number > 0) {
            this.fillTanks(this.ERATH_STACK, false);
            --this.number;
        }

        if (Objects.equals(this.name, "Aqua") && this.number > 0) {
            this.fillTanks(this.WATER_STACK, false);
            --this.number;
        }

        if (Objects.equals(this.name, "Ignis") && this.number > 0) {
            this.fillTanks(this.FIRE_STACK, false);
            --this.number;
        }

        if (Objects.equals(this.name, "Ordo") && this.number > 0) {
            this.fillTanks(this.ORDER_STACK, false);
            --this.number;
        }

        if (Objects.equals(this.name, "Perdition") && this.number > 0) {
            this.fillTanks(this.ENTROPY_STACK, false);
            --this.number;
        }

    }

    public void clearInfused() {
        if (this.getWorld().getTileEntity(this.getPos().add(0, 2, 0)) instanceof TileJarFillable) {
            TileJarFillable s = (TileJarFillable)this.getWorld().getTileEntity(this.getPos().add(0, 2, 0));
            s.amount = 0;
        }

    }

    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (this.isStructureFormed()) {
            textList.add(new TextComponentTranslation("Infused: %s  Amount: %s", new Object[]{this.name, this.storage}));
        }

        textList.add(new TextComponentTranslation("Infused: %s  Amount: %s", new Object[]{this.name, this.number}));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("number", this.number);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.number = data.getInteger("number");
    }

    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.number);
    }

    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.number = buf.readInt();
    }
}
