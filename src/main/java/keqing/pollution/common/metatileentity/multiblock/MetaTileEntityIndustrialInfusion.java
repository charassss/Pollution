package keqing.pollution.common.metatileentity.multiblock;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.RelativeDirection;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.IRenderSetup;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.shader.postprocessing.BloomEffect;
import gregtech.client.shader.postprocessing.BloomType;
import gregtech.client.utils.*;
import gregtech.common.ConfigHolder;
import keqing.pollution.api.block.impl.WrappedIntTired;
import keqing.pollution.api.utils.POUtils;
import keqing.pollution.client.textures.POTextures;
import keqing.pollution.common.block.metablocks.POMBeamCore;
import keqing.pollution.common.block.metablocks.POMagicBlock;
import keqing.pollution.common.block.metablocks.POTurbine;
import keqing.pollution.common.block.PollutionMetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static keqing.pollution.api.predicate.TiredTraceabilityPredicate.*;
import static keqing.pollution.api.predicate.TiredTraceabilityPredicate.CP_GLASS;

public class MetaTileEntityIndustrialInfusion extends MetaTileEntityBaseWithControl implements IBloomEffect, IFastRenderMetaTileEntity {
    private  UUID uuid=null;
    private AspectList aspectList=new AspectList();
    private AspectList al2 = new AspectList();
    private ItemStack outputItem=ItemStack.EMPTY;
    private int tick=0;
    private int timeAmount=0;
    private boolean canOutput=false;
    private int eut=0;
    int glass;
    int coil;
    public MetaTileEntityIndustrialInfusion(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityIndustrialInfusion(this.metaTileEntityId);

    }

    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("             ABA             ", "          BBBABABBB          ", "             ABA             ")
                .aisle("           BBABABB           ", "        BBB C   C BBB        ", "           BBABABB           ")
                .aisle("         BB  ABA  BB         ", "      BBB CBBABABBC BBB      ", "         BB  ABA  BB         ")
                .aisle("       BB           BB       ", "    BBB CBBB D D BBBC BBB    ", "       BB           BB       ")
                .aisle("     BB               BB     ", "   BB CBBB   D D   BBBC BB   ", "     BB               BB     ")
                .aisle("    B  E             E  B    ", "   B BBB     D D     BBB B   ", "    B  E             E  B    ")
                .aisle("    B  E             E  B    ", "  BBCB       D D       BCBB  ", "    B  E             E  B    ")
                .aisle("   B EEE      D      EEE B   ", "  B BB ED    DED    DE BB B  ", "   B EEE      D      EEE B   ")
                .aisle("   B    D     D     D    B   ", " BBCB  DED         DED  BCBB ", "   B    D     D     D    B   ")
                .aisle("  B      D    D    D      B  ", " B BB   DED       DED   BB B ", "  B      D    D    D      B  ")
                .aisle("  B       A   D   A       B  ", "BBCB     DEA  F  AED     BCBB", "  B       A   D   A       B  ")
                .aisle(" B         A     A         B ", "B BB      AEFFFFFEA      BB B", " B         A     A         B ")
                .aisle(" B                         B ", "BCB        FF G FF        BCB", " B                         B ")
                .aisle("AAA          GGG          AAA", "A ADDDDD   F GGG F   DDDDDA A", "AAA          GGG          AAA")
                .aisle("BBB    DDDD  GGG  DDDD    BBB", "B B    E  FFGGGGGFF  E    B B", "BBB    DDDD  GGG  DDDD    BBB")
                .aisle("AAA          GGG          AAA", "A ADDDDD   F GGG F   DDDDDA A", "AAA          GGG          AAA")
                .aisle(" B                         B ", "BCB        FF G FF        BCB", " B                         B ")
                .aisle(" B         A     A         B ", "B BB      AEFFFFFEA      BB B", " B         A     A         B ")
                .aisle("  B       A   D   A       B  ", "BBCB     DEA  F  AED     BCBB", "  B       A   D   A       B  ")
                .aisle("  B      D    D    D      B  ", " B BB   DED       DED   BB B ", "  B      D    D    D      B  ")
                .aisle("   B    D     D     D    B   ", " BBCB  DED         DED  BCBB ", "   B    D     D     D    B   ")
                .aisle("   B EEE      D      EEE B   ", "  B BB ED    DED    DE BB B  ", "   B EEE      D      EEE B   ")
                .aisle("    B  E             E  B    ", "  BBCB       D D       BCBB  ", "    B  E             E  B    ")
                .aisle("    B  E             E  B    ", "   B BBB     D D     BBB B   ", "    B  E             E  B    ")
                .aisle("     BB               BB     ", "   BB CBBB   D D   BBBC BB   ", "     BB               BB     ")
                .aisle("       BB           BB       ", "    BBB CBBB D D BBBC BBB    ", "       BB           BB       ")
                .aisle("         BB  ABA  BB         ", "      BBB CBBABABBC BBB      ", "         BB  ABA  BB         ")
                .aisle("           BBABABB           ", "        BBB C   C BBB        ", "           BBABABB           ")
                .aisle("             ABA             ", "          BBBASABBB          ", "             ABA             ")
                .where('S', selfPredicate())
                .where('A', CP_COIL_CASING)
                .where('B', states(getCasingState())
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(0).setMaxGlobalLimited(2).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(0).setMaxGlobalLimited(2).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(0).setMaxGlobalLimited(2).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(0).setMaxGlobalLimited(2).setPreviewCount(1)
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(0).setMaxGlobalLimited(2).setPreviewCount(1))))
                .where('C', states(getCasingState1()))
                .where('D', states(getCasingState2()))
                .where('E', states(getCasingState3()))
                .where('F', states(getCasingState4()))
                .where('G', CP_GLASS)
                .where(' ', any())
                .build();
    }
    private static IBlockState getCasingState() {
        return PollutionMetaBlocks.MAGIC_BLOCK.getState(POMagicBlock.MagicBlockType.SPELL_PRISM_VOID);
    }
    private static IBlockState getCasingState1(){
        return PollutionMetaBlocks.BEAM_CORE.getState(POMBeamCore.MagicBlockType.BEAM_CORE_4);
    }
    private static IBlockState getCasingState2() {
        return PollutionMetaBlocks.TURBINE.getState(POTurbine.MagicBlockType.TITANIUM_PIPE);
    }
    private static IBlockState getCasingState3() {
        return PollutionMetaBlocks.TURBINE.getState(POTurbine.MagicBlockType.TITANIUM_GEARBOX);
    }
    private static IBlockState getCasingState4() {
        return PollutionMetaBlocks.MAGIC_BLOCK.getState(POMagicBlock.MagicBlockType.MAGIC_BATTERY);
    }
    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if(playerIn.isSneaking())
        {
            setUUID(playerIn.getUniqueID()) ;
        }
        return super.onRightClick(playerIn, hand, facing, hitResult);
    }
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
        this.writeCustomData(1919, (b) -> {
            b.writeUniqueId(this.uuid);
        });
    }
    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == 1919) {
            this.uuid = buf.readUniqueId();
        }
    }
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return POTextures.SPELL_PRISM_VOID;
    }

    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.HPCA_OVERLAY;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object coil = context.get("COILTiredStats");
        this.coil = POUtils.getOrDefault(() -> coil instanceof WrappedIntTired,
                () -> ((WrappedIntTired)coil).getIntTier(),
                0);
        Object glass = context.get("GLASSTiredStats");
        this.glass = POUtils.getOrDefault(() -> glass instanceof WrappedIntTired,
                () -> ((WrappedIntTired)glass).getIntTier(),
                0);
    }
    @Override
    protected void updateFormedValid() {
        if(!this.isActive())
            setActive(true);
        if(!this.getWorld().isRemote && this.inputInventory!=null && this.inputInventory.getSlots()>0 && this.uuid!=null && this.isWorkingEnabled() && this.isActive())
        {
            if(this.energyContainer.getEnergyStored()>480 && this.energyContainer.getInputVoltage()>= GTValues.V[GTValues.HV])
            {
                this.energyContainer.changeEnergy(480);
            }
            else
                return;
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
            InfusionRecipe recipe = null;
            if(list.size()>0 && center!=null && center!=ItemStack.EMPTY && !canOutput)
                recipe =ThaumcraftCraftingManager.findMatchingInfusionRecipe(list, center, getWorld().getPlayerEntityByUUID(this.uuid));
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
             if(canOutput && timeAmount--<0 )
             {
                 canOutput=false;
                 if(this.outputInventory!=null && this.outputInventory.getSlots()>0 && outputItem!=ItemStack.EMPTY)
                     GTTransferUtils.insertItem(this.outputInventory,outputItem,false);
                 this.outputItem=ItemStack.EMPTY;
                 this.timeAmount=0;
             }
        }

    }
    public void addInformation(ItemStack stack,  World world,  List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("pollution.machine.industrial_infusion.tooltip.1", new Object[0]));
        tooltip.add(I18n.format("pollution.machine.industrial_infusion.tooltip.2", new Object[0]));
        tooltip.add(I18n.format("pollution.machine.industrial_infusion.tooltip.3", new Object[0]));
        tooltip.add(I18n.format("pollution.machine.industrial_infusion.tooltip.4", new Object[0]));
        tooltip.add(I18n.format("pollution.machine.industrial_infusion.tooltip.5", new Object[0]));
        tooltip.add(I18n.format("pollution.machine.industrial_infusion.tooltip.6", new Object[0]));
        tooltip.add(I18n.format("pollution.machine.industrial_infusion.tooltip.7", new Object[0]));
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add((new TextComponentTranslation("pollution.machine.output_item",this.outputItem.getDisplayName())).setStyle((new Style()).setColor(TextFormatting.RED)));
        textList.add((new TextComponentTranslation("pollution.machine.canoutput",this.canOutput)).setStyle((new Style()).setColor(TextFormatting.RED)));
        textList.add((new TextComponentTranslation("pollution.machine.process",this.timeAmount)).setStyle((new Style()).setColor(TextFormatting.RED)));
        if(this.aspectList.getAspects().length>0)
        {
            textList.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "======================"));
            for (var s: this.aspectList.getAspects())
            {
                textList.add((new TextComponentTranslation("pollution.machine.aspect",s.getName(),this.aspectList.getAmount(s))).setStyle((new Style()).setColor(TextFormatting.RED)));
            }
            textList.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "======================"));
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
                        if(this.energyContainer!=null && this.energyContainer.getEnergyStored()>128)
                        {
                            this.energyContainer.changeEnergy(-128l);
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
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        this.aspectList.writeToNBT(data,"IndusList");
        data.setBoolean("canOutPut",canOutput);
        if(this.uuid!=null)
            data.setUniqueId("uid",this.uuid);
        data.setInteger("eut",this.eut);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.aspectList.readFromNBT(data,"IndusList");
        this.canOutput = data.getBoolean("canOutPut");
        if(data.hasKey("uidMost"))
            this.uuid = data.getUniqueId("uid");
        this.eut = data.getInteger("eut");

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
    boolean backA;
    int RadomTime;
    @Override
    public void update() {
        super.update();
        if(!backA) if(RadomTime<=10)RadomTime++;
        if(backA) if(RadomTime>=-10)RadomTime--;
        if(RadomTime==10){
            backA = true;
        }
        if(RadomTime==-10){
            backA = false;
        }
        setFusionRingColor(0xFF000000+RadomTime*1250*50);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void renderBloomEffect(BufferBuilder buffer, EffectRenderContext context) {
        int color =this.getFusionRingColor();


        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        EnumFacing relativeBack = RelativeDirection.BACK.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());
        EnumFacing.Axis axis = RelativeDirection.UP.getRelativeFacing(getFrontFacing(), getUpwardsFacing(), isFlipped())
                .getAxis();

        //if (this.isActive()) {
        if (true){
            RenderBufferHelper.renderRing(buffer,
                    getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 14 + 0.5,
                    getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 0.5,
                    getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 14 + 0.5,
                    1, 1, 10, 20,
                    r, g, b, a, EnumFacing.Axis.Y);

            RenderBufferHelper.renderRing(buffer,
                    getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 14 + 0.5,
                    getPos().getY() - context.cameraY() + relativeBack.getYOffset()+ 0.5,
                    getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 14 + 0.5,
                    1, 1, 10, 20,
                    r, g, b, a, EnumFacing.Axis.X);

            RenderBufferHelper.renderRing(buffer,
                    getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 14 + 0.5,
                    getPos().getY() - context.cameraY() + relativeBack.getYOffset()+ 0.5,
                    getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 14 + 0.5,
                    1, 1, 10, 20,
                    r, g, b, a, EnumFacing.Axis.Z);

            RenderBufferHelper.renderRing(buffer,
                    getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 14 + 0.5,
                    getPos().getY() - context.cameraY() + relativeBack.getYOffset()+ 0.5,
                    getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 14 + 0.5,
                    RadomTime, 0.2, 10, 20,
                    r, g, b, a, EnumFacing.Axis.Y);

            RenderBufferHelper.renderRing(buffer,
                    getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 14 + 0.5,
                    getPos().getY() - context.cameraY() + relativeBack.getYOffset()+ 0.5,
                    getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 14 + 0.5,
                    RadomTime+1, 0.2, 10, 20,
                    r, g, b, a, EnumFacing.Axis.Y);

            RenderBufferHelper.renderRing(buffer,
                    getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 14 + 0.5,
                    getPos().getY() - context.cameraY() + relativeBack.getYOffset()+ 0.5,
                    getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 14 + 0.5,
                    RadomTime+2, 0.2, 10, 20,
                    r, g, b, a, EnumFacing.Axis.Y);

            RenderBufferHelper.renderRing(buffer,
                    getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 14 + 0.5,
                    getPos().getY() - context.cameraY() + relativeBack.getYOffset()+ 0.5,
                    getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 14 + 0.5,
                    3, 0.2, 10, 20,
                    r, g, b, a, EnumFacing.Axis.Y);

            RenderBufferHelper.renderRing(buffer,
                    getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 14 + 0.5,
                    getPos().getY() - context.cameraY() + relativeBack.getYOffset()+ 0.5,
                    getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 14 + 0.5,
                    3, 0.2, 10, 20,
                    r, g, b, a, EnumFacing.Axis.X);

            RenderBufferHelper.renderRing(buffer,
                    getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 14 + 0.5,
                    getPos().getY() - context.cameraY() + relativeBack.getYOffset()+ 0.5,
                    getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 14 + 0.5,
                    3, 0.2, 10, 20,
                    r, g, b, a, EnumFacing.Axis.Z);
        }
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

