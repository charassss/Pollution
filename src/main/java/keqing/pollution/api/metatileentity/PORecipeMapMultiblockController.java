package keqing.pollution.api.metatileentity;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTUtility;
import keqing.pollution.api.capability.IVisMultiblock;
import keqing.pollution.api.utils.PollutionLog;
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
import thaumcraft.api.aura.AuraHelper;

import java.util.List;

import static gregtech.api.unification.material.Materials.Lava;
import static keqing.pollution.api.unification.PollutionMaterials.*;
import static keqing.pollution.api.unification.PollutionMaterials.infused_entropy;

public abstract class PORecipeMapMultiblockController extends MultiMapMultiblockController
        implements IVisMultiblock {

    int visStorage;
    int tier;
    int visStorageMax;
    boolean checkVis=false;
    Material  material;
    //这里提供需要要素的接口

    //只需要将我覆写即可，其他的不要动喵
    public Material getMaterial()
    {
        return null;
    }
    //我是多方块是否需要要素的开关，默认开喵

    //下边就不要动了喵
    //定义储罐
    FluidStack AIR_STACK = infused_air.getFluid(1);
    FluidStack FIRE_STACK = infused_fire.getFluid(1);
    FluidStack WATER_STACK = infused_water.getFluid(1);
    FluidStack ERATH_STACK = infused_earth.getFluid(1);
    FluidStack ORDER_STACK = infused_order.getFluid(1);
    FluidStack ENTROPY_STACK = infused_entropy.getFluid(1);

    public boolean isCheckVis(Material material) {
        IMultipleTankHandler inputTank = getInputFluidInventory();
        PollutionLog.logger.info("Check");
        if(material==null)return true;
        if (material == infused_air){
            PollutionLog.logger.info("Require Air");
            if (AIR_STACK.isFluidStackIdentical(inputTank.drain(AIR_STACK, false))) {
                inputTank.drain(AIR_STACK, true);
                PollutionLog.logger.info("Consume Air");
                return true;
            }}

        if(material == infused_fire){
            if (FIRE_STACK.isFluidStackIdentical(inputTank.drain(FIRE_STACK, false))) {
                inputTank.drain(FIRE_STACK, true);
                return true;
            }}

        if (material == infused_water){
            if (WATER_STACK.isFluidStackIdentical(inputTank.drain(WATER_STACK, false))) {
                inputTank.drain(WATER_STACK, true);
                return true;
            }}

        if (material == infused_earth){
            if (ERATH_STACK.isFluidStackIdentical(inputTank.drain(ERATH_STACK, false))) {
                inputTank.drain(ERATH_STACK, true);
                return true;
            }}

        if (material == infused_order){
            if (ORDER_STACK.isFluidStackIdentical(inputTank.drain(ORDER_STACK, false))) {
                inputTank.drain(ORDER_STACK, true);
                return true;
            }}

        if (material == infused_entropy){
            if (ENTROPY_STACK.isFluidStackIdentical(inputTank.drain(ENTROPY_STACK, false))) {
                inputTank.drain(ENTROPY_STACK, true);
                return true;
            }}

        return false;
    }
    public PORecipeMapMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        this(metaTileEntityId, new RecipeMap<?>[] { recipeMap });
    }

    public PORecipeMapMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMaps) {
        super(metaTileEntityId, recipeMaps);
        this.recipeMapWorkable = new POMultiblockRecipeLogic(this);
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        if (isVis())
            tooltip.add(I18n.format("gcym.tooltip.parallel_enabled"));
    }
    int aX = 0;
    int aY = 0;
    int aZ = 0;
    @Override
    public void update() {
        super.update();
        material=getMaterial();
        aX = this.getPos().getX();
        aY = this.getPos().getY();
        aZ = this.getPos().getZ();
        if (AuraHelper.drainVis(getWorld(), getPos(),  (float) (tier*tier*0.1), true) > 0)
        {
            AuraHelper.drainVis(getWorld(), new BlockPos(aX, aY, aZ),  (float) (tier*tier*0.01), false);
            AuraHelper.polluteAura(getWorld(), new BlockPos(aX, aY, aZ),  (float) (tier*tier*0.001), true);

            if(visStorage<visStorageMax)visStorage+=tier*tier;
        }
        if(isActive())if(visStorage>10)visStorage-=tier;
    }
    @Override
    public boolean isActive() {
        return enough()&&this.isStructureFormed() && this.recipeMapWorkable.isActive() && this.recipeMapWorkable.isWorkingEnabled();
    }
    public boolean enough(){
        return visStorage>5;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        tier=this.getAbilities(POMultiblockAbility.VIS_HATCH).get(0).getTier();
        visStorageMax=1000*tier;
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed())
        textList.add(new TextComponentTranslation("Vis: %s / %s Tier:  %s",visStorage,visStorageMax,tier));
        textList.add(new TextComponentTranslation("Infused need : %s Statue: %s",material,isCheckVis(material)));
    }

    @Override
    public boolean isVis() {
        return true;
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("visStorage", this.visStorage);
        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.visStorage = data.getInteger("visStorage");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.visStorage);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.visStorage = buf.readInt();
    }

    public class POMultiblockRecipeLogic  extends MultiblockRecipeLogic {
        public int getn()
        {
            if(visStorage<=50)return 1;
            return visStorage/50;
        }
        @Override
        protected void modifyOverclockPost(int[] resultOverclock,  IRecipePropertyStorage storage) {
            super.modifyOverclockPost(resultOverclock, storage);


            resultOverclock[0] *= 1.0f - getn() * 0.002; // each coil above cupronickel (coilTier = 0) uses 10% less
            // energy
            resultOverclock[0] = Math.max(1, resultOverclock[0]);
        }
        @Override
        public int getParallelLimit() {
            return getn();
        }
        public void setMaxProgress(int maxProgress) {
            this.maxProgressTime = maxProgress/getParallelLimit();
        }
        public POMultiblockRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                if(!enough())this.maxProgressTime++;

                if(enough()&&isCheckVis(material))
                    if (++this.progressTime > this.maxProgressTime) {
                        this.completeRecipe();
                        }
                if (this.hasNotEnoughEnergy && this.getEnergyInputPerSecond() > 19L * (long)this.recipeEUt) {
                    this.hasNotEnoughEnergy = false;
                }
            } else if (this.recipeEUt > 0) {
                this.hasNotEnoughEnergy = true;
                this.decreaseProgress();
            }

        }
    }
}