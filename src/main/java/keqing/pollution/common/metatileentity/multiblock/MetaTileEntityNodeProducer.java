package keqing.pollution.common.metatileentity.multiblock;

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
import keqing.pollution.api.unification.PollutionMaterials;
import keqing.pollution.client.textures.POTextures;
import keqing.pollution.common.block.PollutionMetaBlock.POGlass;
import keqing.pollution.common.block.PollutionMetaBlock.POMBeamCore;
import keqing.pollution.common.block.PollutionMetaBlock.POMagicBlock;
import keqing.pollution.common.block.PollutionMetaBlocks;
import keqing.pollution.common.items.PollutionMetaItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static java.lang.Integer.getInteger;
import static java.lang.Math.*;
import static net.minecraft.util.math.MathHelper.abs;
import static net.minecraft.util.math.MathHelper.ceil;

public class MetaTileEntityNodeProducer extends MetaTileEntityBaseWithControl{
	public MetaTileEntityNodeProducer(ResourceLocation metaTileEntityId) {
		super(metaTileEntityId);
	}

	public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
		return new MetaTileEntityNodeProducer(this.metaTileEntityId);
	}

	//随机数
	private Random random = new Random();
	//计时器和计数器
	private int timer = 0;
	private int tickCount = 0;
	//电压等级
	private int EUtTier;
	//最低输入功率，默认为1920
	private int EUt = 1920;
	//生产时长 默认30s一次有损超频
	private int Duration;
	//每tick源质消耗
	private int infusedCost;

	public ItemStack getRandomAuraNode(){
		ItemStack auraNode = new ItemStack(PollutionMetaItems.PACKAGED_AURA_NODE.getMetaItem(), 1, 100);
		NBTTagCompound nodeTagCompound = new NBTTagCompound();
		double tire_probability = random.nextDouble() * 100; // 生成0到100之间的随机数
		double type_probability = random.nextDouble() * 100;
		int air_probability = min(abs(ceil(random.nextGaussian() * 100)), 1000);
		int fire_probability = min(abs(ceil(random.nextGaussian() * 100)), 1000);
		int water_probability = min(abs(ceil(random.nextGaussian() * 100)), 1000);
		int earth_probability = min(abs(ceil(random.nextGaussian() * 100)), 1000);
		int order_probability = min(abs(ceil(random.nextGaussian() * 100)), 1000);
		int entropy_probability = min(abs(ceil(random.nextGaussian() * 100)), 1000);
		String nodeTire;
		if (tire_probability < 60) {
			nodeTire = "Normal"; // 60%概率生成正常节点
		} else if (tire_probability < 80) {
			nodeTire = "Withering"; // 20%概率生成凋零节点
		} else if (tire_probability < 85) {
			nodeTire = "Bright"; // 5%概率生成明亮节点
		} else {
			nodeTire = "Pale"; // 剩余15%概率生成苍白节点
		}
		nodeTagCompound.setString("NodeTire", nodeTire);
		String nodeType;
		if (type_probability < 60) {
			nodeType = "Standard";
		} else if (type_probability < 70) {
			nodeType = "Ominous";
		} else if (type_probability < 80) {
			nodeType = "Pure";
		} else if (type_probability < 95) {
			nodeType = "Concussive";
		} else {
			nodeType = "Voracious";
		}
		nodeTagCompound.setString("NodeType", nodeType);
		nodeTagCompound.setInteger("EssenceAir", air_probability);
		nodeTagCompound.setInteger("EssenceFire", fire_probability);
		nodeTagCompound.setInteger("EssenceWater", water_probability);
		nodeTagCompound.setInteger("EssenceEarth", earth_probability);
		nodeTagCompound.setInteger("EssenceOrder", order_probability);
		nodeTagCompound.setInteger("EssenceEntropy", entropy_probability);
		auraNode.setTagCompound(nodeTagCompound); // 将NBTTagCompound附加到auraNode上
		return auraNode;
	}

	protected void updateFormedValid() {
		if (!this.isActive()) {
			setActive(true);
		}
		EUtTier = ceil(log((double) this.energyContainer.getInputVoltage() / 32) / log(4) + 1);
		Duration = ceil((float) 30 / (EUtTier - 3));
		infusedCost = (int) (144 * pow(2, EUtTier - 1));
		FluidStack INFUSED_ENERGY = PollutionMaterials.infused_energy.getFluid(infusedCost);
		List<IFluidTank> fluidInputInventory = getAbilities(MultiblockAbility.IMPORT_FLUIDS);
		//检测是否可以工作，输入仓是不是所需流体
		//每个tick都消耗一份流体，如果中途不足，立刻停止配方，计数器也随之恢复
		if (this.isWorkingEnabled() && INFUSED_ENERGY.isFluidStackIdentical(this.inputFluidInventory.drain(INFUSED_ENERGY, false))) {
			if (this.energyContainer.getInputVoltage() > EUt && this.energyContainer.getEnergyStored() >= this.energyContainer.getInputVoltage()) {
				tickCount++;
				this.energyContainer.removeEnergy(this.energyContainer.getInputVoltage());
				if (fluidInputInventory.get(0).getFluidAmount() >= infusedCost) {
					this.inputFluidInventory.drain(INFUSED_ENERGY, true);
				} else {
					timer = 0; //重置进度
				}
				if (tickCount % 20 == 0) { // 每20个tick（1秒）执行一次
					timer++;
					if (timer >= Duration) { // 每duration秒执行一次
						timer = 0; // 重置计时器
						GTTransferUtils.insertItem(this.outputInventory, getRandomAuraNode(), false);
					}
				}
			}
		}
	}

	public void addInformation(ItemStack stack, World world, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, world, tooltip, advanced);
		tooltip.add(I18n.format("pollution.machine.node_producer.tooltip.1", new Object[0]));
		tooltip.add(I18n.format("pollution.machine.node_producer.tooltip.2", new Object[0]));
		tooltip.add(I18n.format("pollution.machine.node_producer.tooltip.3", new Object[0]));
		tooltip.add(I18n.format("pollution.machine.node_producer.tooltip.4", new Object[0]));
	}

	@Override
	protected void addDisplayText(List<ITextComponent> textList) {
		super.addDisplayText(textList);
		textList.add(new TextComponentTranslation("pollution.machine.node_producer_duration", this.Duration).setStyle((new Style()).setColor(TextFormatting.RED)));
		textList.add((new TextComponentTranslation("pollution.machine.node_producer_infusedcost", this.infusedCost)).setStyle((new Style()).setColor(TextFormatting.RED)));
	}

	@Override
	protected BlockPattern createStructurePattern() {
		return FactoryBlockPattern.start()
				.aisle("XXXXX", "XXGGG", "XXXXX")
				.aisle("XXXXX", "XACCG", "XXXXX")
				.aisle("XIXXX", "XSGGG", "XOXXX")
				.where('S', selfPredicate())
				.where('X', states(getCasingState()).setMinGlobalLimited(25)
						.or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1).setPreviewCount(1))
						.or(abilities(MultiblockAbility.INPUT_ENERGY).setMaxGlobalLimited(2).setPreviewCount(1)))
				.where('C', states(getCasingState2()))
				.where('G', states(getCasingState3()))
				.where('I', abilities(MultiblockAbility.IMPORT_FLUIDS).setExactLimit(1).setPreviewCount(1))
				.where('O', abilities(MultiblockAbility.EXPORT_ITEMS).setExactLimit(1).setPreviewCount(1))
				.where('A', air())
				.build();
	}

		private static IBlockState getCasingState() {
			return PollutionMetaBlocks.MAGIC_BLOCK.getState(POMagicBlock.MagicBlockType.SPELL_PRISM_WATER);
		}
		private static IBlockState getCasingState2(){
			return PollutionMetaBlocks.BEAM_CORE.getState(POMBeamCore.MagicBlockType.BEAM_CORE_2);
		}

		private static IBlockState getCasingState3() {
			return PollutionMetaBlocks.GLASS.getState(POGlass.MagicBlockType.CAMINATED_GLASS);
		}

		@Override
		public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
			return POTextures.SPELL_PRISM_WATER;
		}

		@Override
		protected OrientedOverlayRenderer getFrontOverlay() {
			return Textures.HPCA_OVERLAY;
		}

}

