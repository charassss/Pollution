package keqing.pollution.common.metatileentity.multiblock;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.util.GTTransferUtils;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import keqing.pollution.api.metatileentity.POMultiblockAbility;
import keqing.pollution.client.textures.POTextures;
import keqing.pollution.common.block.PollutionMetaBlock.POGlass;
import keqing.pollution.common.block.PollutionMetaBlock.POMBeamCore;
import keqing.pollution.common.block.PollutionMetaBlock.POMagicBlock;
import keqing.pollution.common.block.PollutionMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MetaTileEntityIndustrialInfusion extends MetaTileEntityBaseWithControl{
    private  UUID uuid=null;
    private AspectList aspectList=new AspectList();
    private AspectList al2 = new AspectList();
    private ItemStack outputItem=ItemStack.EMPTY;
    private int tick=0;
    private int timeAmount=0;
    private boolean canOutput=false;
    public MetaTileEntityIndustrialInfusion(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityIndustrialInfusion(this.metaTileEntityId);

    }
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXXXX", "XXXXXXX", "XXXXXXX", "##XXXXX")
                .aisle("XXXXXXX", "XAXCCCX", "XXXAAAX", "##XXXXX")
                .aisle("XXXXXXX", "XAXCCCX", "XXXAAAX", "##XXXXX")
                .aisle("XXXXXXX", "XSXDDDX", "XFXDDDX", "##XXXXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(60)
                        .or( abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(1).setPreviewCount(1))
                                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(1).setPreviewCount(1))
                                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1)))
                .where('C', states(getCasingState2()))
                .where('D', states(getCasingState3()))
                .where('F', abilities(POMultiblockAbility.VIS_HATCH).setMaxGlobalLimited(1).setPreviewCount(1))
                .where('A', air())
                .where('#', any())
                .build();
    }
    private static IBlockState getCasingState() {
        return PollutionMetaBlocks.MAGIC_BLOCK.getState(POMagicBlock.MagicBlockType.SPELL_PRISM_HOT);
    }
    private static IBlockState getCasingState2(){
        return PollutionMetaBlocks.BEAM_CORE.getState(POMBeamCore.MagicBlockType.BEAM_CORE_4);
    }

    private static IBlockState getCasingState3() {
        return PollutionMetaBlocks.GLASS.getState(POGlass.MagicBlockType.AAMINATED_GLASS);
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if(playerIn.isSneaking())
        {
            this.uuid = playerIn.getUniqueID();
        }
        return super.onRightClick(playerIn, hand, facing, hitResult);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return POTextures.SPELL_PRISM_HOT;
    }

    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.CUTTER_OVERLAY;
    }

    @Override
    protected void updateFormedValid() {
        if(!this.getWorld().isRemote && this.inputInventory!=null && this.inputInventory.getSlots()>0 && this.uuid!=null)
        {
            if(++tick>=20)
            {
                getAspectFromWorld();
                tick=0;
            }

            ItemStack center = ItemStack.EMPTY;
            ArrayList<ItemStack> list = new ArrayList<>();
            for (int i = 0; i < this.inputInventory.getSlots(); i++) {
                if(!this.inputInventory.getStackInSlot(i).isEmpty() && this.inputInventory.getStackInSlot(i).getItem()!= Items.AIR)
                {
                    if(i==0)
                        center = this.inputInventory.getStackInSlot(i);
                    else
                        list.add(this.inputInventory.getStackInSlot(i));
                }
            }
            InfusionRecipe recipe = ThaumcraftCraftingManager.findMatchingInfusionRecipe(list, center, getWorld().getPlayerEntityByUUID(this.uuid));
            if(recipe!=null && !canOutput)
            {
                outputItem = ((ItemStack)recipe.recipeOutput).copy();
                AspectList al = recipe.getAspects(getWorld().getPlayerEntityByUUID(this.uuid), center, list);

                Aspect[] var7 = al.getAspects();
                int var8 = var7.length;
                timeAmount=0;
                al2 = new AspectList();
                for(int var9 = 0; var9 < var8; ++var9) {
                    Aspect as = var7[var9];
                    if ((int)((float)al.getAmount(as))*0.5 > 0) {
                        al2.add(as, (int)((float)al.getAmount(as)*0.5));
                    }
                    timeAmount += al.getAmount(as);
                }
                //判断缓存源质是否够用
                boolean bl =true;
                for (var a:al2.getAspects())
                {
                    if(this.aspectList.getAmount(a) <al2.getAmount(a))
                    {
                        bl=false;
                    }
                }
                //此物品够热 可以开机 消耗掉缓存源质和物品
                if(bl==true)
                {
                    for (var a:al2.getAspects())
                    {
                        this.aspectList.reduce(a,al2.getAmount(a));
                    }
                    for (int i = 0; i < this.inputInventory.getSlots(); i++) {
                        if(!this.inputInventory.getStackInSlot(i).isEmpty() && this.inputInventory.getStackInSlot(i).getItem()!= Items.AIR)
                        {
                            this.inputInventory.extractItem(i,this.inputInventory.getStackInSlot(i).getCount(),false);
                        }
                    }

                }
                this.canOutput=bl;
            }
            //是否可以耗电 可以耗电继续进行进度结算
            if(this.energyContainer.getEnergyStored()>2000 && this.energyContainer.getInputVoltage()>= GTValues.EV)
                if(canOutput && timeAmount--<0 && outputItem!=ItemStack.EMPTY)
                {
                    this.energyContainer.changeEnergy(2000);
                    canOutput=false;
                    if(this.outputInventory!=null && this.outputInventory.getSlots()>0)
                        GTTransferUtils.insertItem(this.outputInventory,outputItem,false);
                    this.timeAmount=0;
                }
        }

    }
    private void getAspectFromWorld()
    {
        //中心位置
        BlockPos centerPos=this.getPos() ;
        int radius = 7;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos currentPos = centerPos.add(x, y, z);
                    TileEntity te = this.getWorld().getTileEntity(currentPos);
                    if(te!= null && te instanceof IAspectSource)
                    {
                        final var s=(IAspectSource)te;
                        for (Aspect a:s.getAspects().getAspects())
                        {
                            if(this.aspectList.getAmount(a)<1000)
                            {
                                int max = Math.min(1000-this.aspectList.getAmount(a),s.containerContains(a));
                                if(s.takeFromContainer(a,max))
                                {
                                    this.aspectList.merge(a,max+this.aspectList.getAmount(a));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        this.aspectList.writeToNBT(data,"IndusList");
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.aspectList.readFromNBT(data,"IndusList");
    }
}
