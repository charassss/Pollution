package keqing.pollution.common.metatileentity.multiblock;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTTransferUtils;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtechfoodoption.machines.farmer.NetherWartFarmerMode;
import keqing.pollution.Pollution;
import keqing.pollution.api.metatileentity.POMultiblockAbility;
import keqing.pollution.api.unification.PollutionMaterials;
import keqing.pollution.client.textures.POTextures;
import keqing.pollution.common.block.PollutionMetaBlock.POMagicBlock;
import keqing.pollution.common.block.PollutionMetaBlock.POTurbine;
import keqing.pollution.common.block.PollutionMetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
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
import static net.minecraft.util.math.MathHelper.ceil;

public class MetaTileEntityEssenceCollector extends MetaTileEntityBaseWithControl{
    //基础产速
    private float basicSpeedPerTick = 0.05f;
    //最终产速
    private int finalSpeedPerTick = 0;
    //是否有催化升级
    private boolean hasCatalystUpgrade = false;
    //催化升级等级
    private int catalystUpgradeTier = 0;
    //最低输入功率，默认为30
    private int EUt = 30;
    //输出单种流体的类型,只在聚焦时
    private Material type;

    public MetaTileEntityEssenceCollector(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityEssenceCollector(this.metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("BXOXB", "XXXXX", "BXXXB")
                .aisle("IXXXI", "XHGHX", "XXXXX")
                .aisle("IXXXI", "XGHGX", "XXXXX")
                .aisle("IXXXI", "XHGHX", "XXXXX")
                .aisle("BXXXB", "XXSXX", "BXXXB")
                .where('S', selfPredicate())
                .where('B', any())
                .where('A', air())
                .where('X', states(getCasingState()).setMinGlobalLimited(40)
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMaxGlobalLimited(2).setPreviewCount(1)))
                .where('G', states(getCasingState2()))
                .where('H', states(getCasingState3()))
                .where('I', abilities(MultiblockAbility.EXPORT_FLUIDS).setExactLimit(6).setPreviewCount(6))
                .where('O', abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(1).setPreviewCount(1))
                .build();
    }
    private static IBlockState getCasingState() {
        return PollutionMetaBlocks.MAGIC_BLOCK.getState(POMagicBlock.MagicBlockType.SPELL_PRISM_AIR);
    }

    private static IBlockState getCasingState2() {
        return PollutionMetaBlocks.TURBINE.getState(POTurbine.MagicBlockType.STEEL_PIPE);
    }

    private static IBlockState getCasingState3() {
        return PollutionMetaBlocks.TURBINE.getState(POTurbine.MagicBlockType.STEEL_GEARBOX);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return POTextures.SPELL_PRISM_AIR;
    }

    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.HPCA_OVERLAY;
    }

    @Override
    protected void updateFormedValid() {
        int aX = this.getPos().getX();
        int aY = this.getPos().getY();
        int aZ = this.getPos().getZ();
        if (!this.isActive()) {
            setActive(true);
        }
        //4^(tier-1) * 32 =tier对应的实际电压
        //tier=log4( V / 32 ) + 1
        if (this.isWorkingEnabled() && this.isActive()) {
            //统计灵气，污染
            float visThisChunk = AuraHelper.getVis(this.getWorld(), new BlockPos(aX, aY, aZ));
            float fluxThisChunk = AuraHelper.getFlux(this.getWorld(), new BlockPos(aX, aY, aZ));
            //将能源仓的输入电压转化为GT的电压等级
            int EUtTier = ceil(log((double) this.energyContainer.getInputVoltage() / 32) / log(4) + 1);
            //计算最终产速
            if (fluxThisChunk >= visThisChunk) {
                finalSpeedPerTick = 0;
            }
            else {
                finalSpeedPerTick = ceil(basicSpeedPerTick * pow(2, EUtTier) * (1 - fluxThisChunk / visThisChunk) * log(visThisChunk));
            }
            //有聚焦器的情况，也就是输入总线第一格不是空的，此时判断是否有电
            //如果是六种聚焦器（水晶）之一，则说明有聚焦器，再判断是否有仓，且仓是否够大，然后扣电，然后输出一种流体；
            //如果聚焦器不是六种之一，或者输入总线什么都没有，进入一般模式产出六种

            if (!this.inputInventory.getStackInSlot(0).isEmpty()
                    && this.energyContainer.getEnergyStored() > this.energyContainer.getInputVoltage()
                    && this.energyContainer.getInputVoltage() >= EUt) {
                if (this.inputInventory.getStackInSlot(0).getTranslationKey().equals(new ItemStack(BlocksTC.crystalAir).getTranslationKey())){
                    type = PollutionMaterials.infused_air;
                    if (this.outputFluidInventory != null && this.outputFluidInventory.getTanks() >= finalSpeedPerTick) {
                        this.energyContainer.changeEnergy(this.energyContainer.getInputVoltage());
                        GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, Collections.singletonList(type.getFluid(finalSpeedPerTick * 3)));
                    }
                }
                else if (this.inputInventory.getStackInSlot(0).getTranslationKey().equals(new ItemStack(BlocksTC.crystalFire).getTranslationKey())){
                    type = PollutionMaterials.infused_fire;
                    if (this.outputFluidInventory != null && this.outputFluidInventory.getTanks() >= finalSpeedPerTick) {
                        this.energyContainer.changeEnergy(this.energyContainer.getInputVoltage());
                        GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, Collections.singletonList(type.getFluid(finalSpeedPerTick * 3)));
                    }
                }
                else if (this.inputInventory.getStackInSlot(0).getTranslationKey().equals(new ItemStack(BlocksTC.crystalEarth).getTranslationKey())){
                    type = PollutionMaterials.infused_earth;
                    if (this.outputFluidInventory != null && this.outputFluidInventory.getTanks() >= finalSpeedPerTick) {
                        this.energyContainer.changeEnergy(this.energyContainer.getInputVoltage());
                        GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, Collections.singletonList(type.getFluid(finalSpeedPerTick * 3)));
                    }
                }
                else if (this.inputInventory.getStackInSlot(0).getTranslationKey().equals(new ItemStack(BlocksTC.crystalWater).getTranslationKey())){
                    type = PollutionMaterials.infused_water;
                    if (this.outputFluidInventory != null && this.outputFluidInventory.getTanks() >= finalSpeedPerTick) {
                        this.energyContainer.changeEnergy(this.energyContainer.getInputVoltage());
                        GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, Collections.singletonList(type.getFluid(finalSpeedPerTick * 3)));
                    }
                }
                else if (this.inputInventory.getStackInSlot(0).getTranslationKey().equals(new ItemStack(BlocksTC.crystalOrder).getTranslationKey())){
                    type = PollutionMaterials.infused_order;
                    if (this.outputFluidInventory != null && this.outputFluidInventory.getTanks() >= finalSpeedPerTick) {
                        this.energyContainer.changeEnergy(this.energyContainer.getInputVoltage());
                        GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, Collections.singletonList(type.getFluid(finalSpeedPerTick * 3)));
                    }
                }
                else if (this.inputInventory.getStackInSlot(0).getTranslationKey().equals(new ItemStack(BlocksTC.crystalEntropy).getTranslationKey())){
                    type = PollutionMaterials.infused_entropy;
                    if (this.outputFluidInventory != null && this.outputFluidInventory.getTanks() >= finalSpeedPerTick) {
                        this.energyContainer.changeEnergy(this.energyContainer.getInputVoltage());
                        GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, Collections.singletonList(type.getFluid(finalSpeedPerTick * 3)));
                    }
                }
            }
            else if (this.energyContainer.getEnergyStored() > 480 && this.inputInventory.getStackInSlot(0).isEmpty()) {
                if (this.outputFluidInventory != null && this.outputFluidInventory.getTanks() >= finalSpeedPerTick) {
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
    }}
