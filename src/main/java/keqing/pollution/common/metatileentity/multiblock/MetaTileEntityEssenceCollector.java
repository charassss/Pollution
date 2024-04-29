package keqing.pollution.common.metatileentity.multiblock;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtechfoodoption.machines.farmer.NetherWartFarmerMode;
import keqing.pollution.Pollution;
import keqing.pollution.api.block.impl.WrappedIntTired;
import keqing.pollution.api.metatileentity.POMultiblockAbility;
import keqing.pollution.api.unification.PollutionMaterials;
import keqing.pollution.api.utils.POUtils;
import keqing.pollution.client.textures.POTextures;
import keqing.pollution.common.block.PollutionMetaBlock.POGlass;
import keqing.pollution.common.block.PollutionMetaBlock.POMBeamCore;
import keqing.pollution.common.block.PollutionMetaBlock.POMagicBlock;
import keqing.pollution.common.block.PollutionMetaBlock.POTurbine;
import keqing.pollution.common.block.PollutionMetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.openal.EFXUtil;
import scala.xml.dtd.EMPTY;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemsTC;

import java.util.Collections;
import java.util.List;

import static java.lang.Math.log;
import static java.lang.Math.pow;
import static keqing.pollution.api.predicate.TiredTraceabilityPredicate.CP_COIL_CASING;
import static net.minecraft.util.math.MathHelper.ceil;

public class MetaTileEntityEssenceCollector extends MetaTileEntityBaseWithControl {
    //基础产速
    private float basicSpeedPerTick = 0.025f;
    //最终产速
    private int finalSpeedPerTick = 0;
    //是否聚焦
    private boolean isFocused = false;
    //电压等级
    private int EUtTier = 0;
    //线圈等级
    private int coilLevel = 0;
    //最低输入功率，默认为30
    private int EUt = 30;
    //输出单种流体的类型,只在聚焦时
    private Material type;
    //当前区块灵气、污染
    float visThisChunk;
    float fluxThisChunk;

    public MetaTileEntityEssenceCollector(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityEssenceCollector(this.metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("      AAA      ", "      AAA      ", "      CCC      ", "               ", "               ", "               ", "               ", "               ", "      CCC      ", "      AAA      ", "      AAA      ")
                .aisle("   AAAABAAAA   ", "   ACE   ECA   ", "     A   A     ", "     A   A     ", "     A   A     ", "     A   A     ", "     A   A     ", "     A   A     ", "     A   A     ", "   ACE   ECA   ", "   AAAABAAAA   ")
                .aisle("  AAFFABAFFAA  ", "  A         A  ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "  A         A  ", "  AAFFABAFFAA  ")
                .aisle(" AAFFEABAEFFAA ", " C           C ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", " C           C ", " AAFFEABAEFFAA ")
                .aisle(" AFFEFABAFEFFA ", " E   I   K   E ", " A   I   K   A ", " A   D   D   A ", " A   E   E   A ", " A           A ", " A   E   E   A ", " A   A   A   A ", " A   I   K   A ", " E   I   K   E ", " AFFEFABAFEFFA ")
                .aisle("AAAAAAABAAAAAAA", "A             A", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "A             A", "AAAAAAABAAAAAAA")
                .aisle("ABBBFBBEBBFBBBA", "A   G     L   A", "    G     L    ", "    D     D    ", "    E     E    ", "               ", "    E     E    ", "    A     A    ", "    G     L    ", "A   G     L   A", "ABBBFBBEBBFBBBA")
                .aisle("AAAAAAABAAAAAAA", "A             A", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "A             A", "AAAAAAABAAAAAAA")
                .aisle(" AFFEFABAFEFFA ", " E   H   J   E ", " A   H   J   A ", " A   D   D   A ", " A   E   E   A ", " A           A ", " A   E   E   A ", " A   A   A   A ", " A   H   J   A ", " E   H   J   E ", " AFFEFABAFEFFA ")
                .aisle(" AAFFEABAEFFAA ", " C           C ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", " C           C ", " AAFFEABAEFFAA ")
                .aisle("  AAFFABAFFAA  ", "  A         A  ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "  A         A  ", "  AAFFABAFFAA  ")
                .aisle("   AAAABAAAA   ", "   ACE   ECA   ", "     A   A     ", "     A   A     ", "     A   A     ", "     A   A     ", "     A   A     ", "     A   A     ", "     A   A     ", "   ACE   ECA   ", "   AAAABAAAA   ")
                .aisle("      AOA      ", "      ASA      ", "      CCC      ", "               ", "               ", "               ", "               ", "               ", "      CCC      ", "      AAA      ", "      AAA      ")

                .where('S', selfPredicate())
                .where(' ', any())
                .where('A', states(getCasingState()).setMinGlobalLimited(200)
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMaxGlobalLimited(2).setPreviewCount(1)))
                .where('B', CP_COIL_CASING)
                .where('C', states(getCasingState2()))
                .where('E', states(getCasingState3()))
                .where('F', states(getCasingState4()))
                .where('D', abilities(MultiblockAbility.EXPORT_FLUIDS).setExactLimit(6).setPreviewCount(6))
                .where('O', abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(2).setPreviewCount(1))
                .where('G', states(getCasingState5()))
                .where('H', states(getCasingState6()))
                .where('I', states(getCasingState7()))
                .where('J', states(getCasingState8()))
                .where('K', states(getCasingState9()))
                .where('L', states(getCasingState10()))
                .build();
    }

    private static IBlockState getCasingState() {
        return PollutionMetaBlocks.MAGIC_BLOCK.getState(POMagicBlock.MagicBlockType.SPELL_PRISM);
    }

    private static IBlockState getCasingState2() {
        return PollutionMetaBlocks.GLASS.getState(POGlass.MagicBlockType.LAMINATED_GLASS);
    }

    private static IBlockState getCasingState3() {
        return PollutionMetaBlocks.BEAM_CORE.getState(POMBeamCore.MagicBlockType.BEAM_CORE_4);
    }

    private static IBlockState getCasingState4() {
        return PollutionMetaBlocks.TURBINE.getState(POTurbine.MagicBlockType.STAINLESS_STEEL_GEARBOX);
    }

    private static IBlockState getCasingState5() {
        return PollutionMetaBlocks.MAGIC_BLOCK.getState(POMagicBlock.MagicBlockType.VOID_PRISM);
    }

    private static IBlockState getCasingState6() {
        return PollutionMetaBlocks.MAGIC_BLOCK.getState(POMagicBlock.MagicBlockType.SPELL_PRISM_EARTH);
    }

    private static IBlockState getCasingState7() {
        return PollutionMetaBlocks.MAGIC_BLOCK.getState(POMagicBlock.MagicBlockType.SPELL_PRISM_WATER);
    }

    private static IBlockState getCasingState8() {
        return PollutionMetaBlocks.MAGIC_BLOCK.getState(POMagicBlock.MagicBlockType.SPELL_PRISM_HOT);
    }

    private static IBlockState getCasingState9() {
        return PollutionMetaBlocks.MAGIC_BLOCK.getState(POMagicBlock.MagicBlockType.SPELL_PRISM_AIR);
    }

    private static IBlockState getCasingState10() {
        return PollutionMetaBlocks.MAGIC_BLOCK.getState(POMagicBlock.MagicBlockType.SPELL_PRISM_ORDER);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return POTextures.SPELL_PRISM;
    }

    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.HPCA_OVERLAY;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object coilLevel = context.get("COILTiredStats");
        this.coilLevel = POUtils.getOrDefault(() -> coilLevel instanceof WrappedIntTired,
                () -> ((WrappedIntTired) coilLevel).getIntTier(),
                0);
    }

    @Override
    protected void updateFormedValid() {
        int aX = this.getPos().getX();
        int aY = this.getPos().getY();
        int aZ = this.getPos().getZ();
        if (!this.isActive()) {
            setActive(true);
        }
        //4^(tier-1)*32=对应的实际电压V
        //计算得出tier = log4(V/32)+1
        if (this.isWorkingEnabled() && this.isActive()) {
            //统计灵气，污染
            visThisChunk = AuraHelper.getVis(this.getWorld(), new BlockPos(aX, aY, aZ));
            fluxThisChunk = AuraHelper.getFlux(this.getWorld(), new BlockPos(aX, aY, aZ));
            //将能源仓的输入电压转化为GT的电压等级
            EUtTier = ceil(log((double) this.energyContainer.getInputVoltage() / 32) / log(4) + 1);
            //计算最终产速
            if (fluxThisChunk >= visThisChunk) {
                finalSpeedPerTick = 0;
            } else {
                finalSpeedPerTick = ceil(basicSpeedPerTick * (1 + coilLevel) * pow(2, EUtTier) * (1 - fluxThisChunk / visThisChunk) * log(visThisChunk));
            }
            //有聚焦器的情况，也就是输入总线第一格不是空的，此时判断是否有电
            //如果是六种聚焦器（水晶）之一，则说明有聚焦器，再判断是否有仓，且仓是否够大，然后扣电，然后输出一种流体；
            //如果聚焦器不是六种之一，或者输入总线什么都没有，进入一般模式产出六种
            if (!this.inputInventory.getStackInSlot(0).isEmpty()
                    && this.energyContainer.getEnergyStored() > this.energyContainer.getInputVoltage()
                    && this.energyContainer.getInputVoltage() >= EUt) {
                if (this.inputInventory.getStackInSlot(0).getTranslationKey().equals(new ItemStack(BlocksTC.crystalAir).getTranslationKey())) {
                    type = PollutionMaterials.infused_air;
                    isFocused = true;
                    if (this.outputFluidInventory != null && this.outputFluidInventory.getTanks() >= finalSpeedPerTick) {
                        this.energyContainer.changeEnergy(this.energyContainer.getInputVoltage());
                        GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, Collections.singletonList(type.getFluid(finalSpeedPerTick * 3)));
                    }
                } else if (this.inputInventory.getStackInSlot(0).getTranslationKey().equals(new ItemStack(BlocksTC.crystalFire).getTranslationKey())) {
                    type = PollutionMaterials.infused_fire;
                    isFocused = true;
                    if (this.outputFluidInventory != null && this.outputFluidInventory.getTanks() >= finalSpeedPerTick) {
                        this.energyContainer.changeEnergy(this.energyContainer.getInputVoltage());
                        GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, Collections.singletonList(type.getFluid(finalSpeedPerTick * 3)));
                    }
                } else if (this.inputInventory.getStackInSlot(0).getTranslationKey().equals(new ItemStack(BlocksTC.crystalEarth).getTranslationKey())) {
                    type = PollutionMaterials.infused_earth;
                    isFocused = true;
                    if (this.outputFluidInventory != null && this.outputFluidInventory.getTanks() >= finalSpeedPerTick) {
                        this.energyContainer.changeEnergy(this.energyContainer.getInputVoltage());
                        GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, Collections.singletonList(type.getFluid(finalSpeedPerTick * 3)));
                    }
                } else if (this.inputInventory.getStackInSlot(0).getTranslationKey().equals(new ItemStack(BlocksTC.crystalWater).getTranslationKey())) {
                    type = PollutionMaterials.infused_water;
                    isFocused = true;
                    if (this.outputFluidInventory != null && this.outputFluidInventory.getTanks() >= finalSpeedPerTick) {
                        this.energyContainer.changeEnergy(this.energyContainer.getInputVoltage());
                        GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, Collections.singletonList(type.getFluid(finalSpeedPerTick * 3)));
                    }
                } else if (this.inputInventory.getStackInSlot(0).getTranslationKey().equals(new ItemStack(BlocksTC.crystalOrder).getTranslationKey())) {
                    type = PollutionMaterials.infused_order;
                    isFocused = true;
                    if (this.outputFluidInventory != null && this.outputFluidInventory.getTanks() >= finalSpeedPerTick) {
                        this.energyContainer.changeEnergy(this.energyContainer.getInputVoltage());
                        GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, Collections.singletonList(type.getFluid(finalSpeedPerTick * 3)));
                    }
                } else if (this.inputInventory.getStackInSlot(0).getTranslationKey().equals(new ItemStack(BlocksTC.crystalEntropy).getTranslationKey())) {
                    type = PollutionMaterials.infused_entropy;
                    isFocused = true;
                    if (this.outputFluidInventory != null && this.outputFluidInventory.getTanks() >= finalSpeedPerTick) {
                        this.energyContainer.changeEnergy(this.energyContainer.getInputVoltage());
                        GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, Collections.singletonList(type.getFluid(finalSpeedPerTick * 3)));
                    }
                }
            } else if (this.energyContainer.getEnergyStored() > 480 && this.inputInventory.getStackInSlot(0).isEmpty()) {
                if (this.outputFluidInventory != null && this.outputFluidInventory.getTanks() >= finalSpeedPerTick) {
                    isFocused = false;
                    this.energyContainer.removeEnergy(this.energyContainer.getInputVoltage());
                    GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false,
                            Collections.singletonList(PollutionMaterials.infused_air.getFluid(finalSpeedPerTick)));
                    GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false,
                            Collections.singletonList(PollutionMaterials.infused_fire.getFluid(finalSpeedPerTick)));
                    GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false,
                            Collections.singletonList(PollutionMaterials.infused_earth.getFluid(finalSpeedPerTick)));
                    GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false,
                            Collections.singletonList(PollutionMaterials.infused_water.getFluid(finalSpeedPerTick)));
                    GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false,
                            Collections.singletonList(PollutionMaterials.infused_order.getFluid(finalSpeedPerTick)));
                    GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false,
                            Collections.singletonList(PollutionMaterials.infused_entropy.getFluid(finalSpeedPerTick)));
                }
            }
        }
    }

    public void addInformation(ItemStack stack, World world, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("pollution.machine.essence_collector.tooltip.1", new Object[0]));
        tooltip.add(I18n.format("pollution.machine.essence_collector.tooltip.2", new Object[0]));
        tooltip.add(I18n.format("pollution.machine.essence_collector.tooltip.3", new Object[0]));
        tooltip.add(I18n.format("pollution.machine.essence_collector.tooltip.4", new Object[0]));
        tooltip.add(I18n.format("pollution.machine.essence_collector.tooltip.5", new Object[0]));
        tooltip.add(I18n.format("pollution.machine.essence_collector.tooltip.6", new Object[0]));
    }


    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("pollution.machine.essence_collector_visthischunk", this.visThisChunk).setStyle((new Style()).setColor(TextFormatting.RED)));
        textList.add((new TextComponentTranslation("pollution.machine.essence_collector_fluxthischunk", this.fluxThisChunk)).setStyle((new Style()).setColor(TextFormatting.RED)));
        textList.add((new TextComponentTranslation("pollution.machine.essence_collector_coillevel", this.coilLevel)).setStyle((new Style()).setColor(TextFormatting.RED)));
        textList.add((new TextComponentTranslation("pollution.machine.essence_collector_finalspeedpertick", this.finalSpeedPerTick)).setStyle((new Style()).setColor(TextFormatting.RED)));
        textList.add((new TextComponentTranslation("pollution.machine.essence_collector_euttier", this.EUtTier)).setStyle((new Style()).setColor(TextFormatting.RED)));
        if(isFocused){
            textList.add((new TextComponentTranslation("pollution.machine.essence_collector_iffocused", "开启")).setStyle((new Style()).setColor(TextFormatting.RED)));
        }
        else{
            textList.add((new TextComponentTranslation("pollution.machine.essence_collector_iffocused", "关闭")).setStyle((new Style()).setColor(TextFormatting.RED)));
        }
    }}
