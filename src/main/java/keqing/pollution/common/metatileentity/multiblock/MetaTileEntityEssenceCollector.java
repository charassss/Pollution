package keqing.pollution.common.metatileentity.multiblock;

import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.RelativeDirection;
import gregtech.api.util.interpolate.Eases;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.IRenderSetup;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.shader.postprocessing.BloomEffect;
import gregtech.client.shader.postprocessing.BloomType;
import gregtech.client.utils.*;
import gregtech.common.ConfigHolder;
import keqing.pollution.api.block.impl.WrappedIntTired;
import keqing.pollution.api.unification.PollutionMaterials;
import keqing.pollution.api.utils.POUtils;
import keqing.pollution.client.textures.POTextures;
import keqing.pollution.common.block.metablocks.POGlass;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
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
import scala.actors.threadpool.Arrays;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.blocks.BlocksTC;

import java.util.*;

import static java.lang.Math.log;
import static java.lang.Math.pow;
import static keqing.pollution.api.predicate.TiredTraceabilityPredicate.CP_COIL_CASING;
import static net.minecraft.util.math.MathHelper.ceil;

public class MetaTileEntityEssenceCollector extends MetaTileEntityBaseWithControl implements IBloomEffect, IFastRenderMetaTileEntity {
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

	private static Map<Item, Material> materialMap = new HashMap<>();

	static {
		materialMap.put(Item.getItemFromBlock(BlocksTC.crystalAir), PollutionMaterials.infused_air);
		materialMap.put(Item.getItemFromBlock(BlocksTC.crystalFire), PollutionMaterials.infused_fire);
		materialMap.put(Item.getItemFromBlock(BlocksTC.crystalEarth), PollutionMaterials.infused_earth);
		materialMap.put(Item.getItemFromBlock(BlocksTC.crystalOrder), PollutionMaterials.infused_order);
		materialMap.put(Item.getItemFromBlock(BlocksTC.crystalEntropy), PollutionMaterials.infused_entropy);
		materialMap.put(Item.getItemFromBlock(BlocksTC.crystalWater), PollutionMaterials.infused_water);
	}

	public MetaTileEntityEssenceCollector(ResourceLocation metaTileEntityId) {
		super(metaTileEntityId);
	}

	public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
		return new MetaTileEntityEssenceCollector(this.metaTileEntityId);
	}

	@Override
	public List<ITextComponent> getDataInfo() {
		return Collections.emptyList();
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
		return PollutionMetaBlocks.MAGIC_BLOCK.getState(POMagicBlock.MagicBlockType.SPELL_PRISM_VOID);
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

	boolean backA;

	@Override
	public void update() {
		super.update();
		if (!backA) if (RadomTime <= 10) ++RadomTime;
		if (backA) if (RadomTime >= -10) --RadomTime;
		if (RadomTime == 10) {
			backA = true;
		}
		if (RadomTime == -10) {
			backA = false;
		}
		setFusionRingColor(0xFF000000 | (RadomTime * 1250 * 50));
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

				this.type = MetaTileEntityEssenceCollector.materialMap.get(this.getInputInventory().getStackInSlot(0).getItem());
				this.isFocused = true;
				if (this.outputFluidInventory != null && this.outputFluidInventory.getTanks() >= finalSpeedPerTick) {
					this.energyContainer.changeEnergy(this.energyContainer.getInputVoltage());
					GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory, false, Collections.singletonList(type.getFluid(finalSpeedPerTick * 3)));
				}
			} else if (this.energyContainer.getEnergyStored() > 480 && this.inputInventory.getStackInSlot(0).isEmpty()) {
				if (this.outputFluidInventory != null && this.outputFluidInventory.getTanks() >= finalSpeedPerTick) {
					isFocused = false;
					this.energyContainer.removeEnergy(this.energyContainer.getInputVoltage());
					GTTransferUtils.addFluidsToFluidHandler(this.outputFluidInventory,
							false,
							Arrays.asList(new Object[]{PollutionMaterials.infused_air.getFluid(finalSpeedPerTick),
									PollutionMaterials.infused_fire.getFluid(finalSpeedPerTick),
									PollutionMaterials.infused_earth.getFluid(finalSpeedPerTick),
									PollutionMaterials.infused_water.getFluid(finalSpeedPerTick),
									PollutionMaterials.infused_order.getFluid(finalSpeedPerTick),
									PollutionMaterials.infused_entropy.getFluid(finalSpeedPerTick)})
					);
				}
			}
		}
	}

	public void addInformation(ItemStack stack, World world, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, world, tooltip, advanced);
		tooltip.add(I18n.format("pollution.machine.essence_collector.tooltip.1"));
		tooltip.add(I18n.format("pollution.machine.essence_collector.tooltip.2"));
		tooltip.add(I18n.format("pollution.machine.essence_collector.tooltip.3"));
		tooltip.add(I18n.format("pollution.machine.essence_collector.tooltip.4"));
		tooltip.add(I18n.format("pollution.machine.essence_collector.tooltip.5"));
		tooltip.add(I18n.format("pollution.machine.essence_collector.tooltip.6"));
	}


	@Override
	protected void addDisplayText(List<ITextComponent> textList) {
		super.addDisplayText(textList);
		textList.add(new TextComponentTranslation("pollution.machine.essence_collector_visthischunk", this.visThisChunk).setStyle((new Style()).setColor(TextFormatting.RED)));
		textList.add((new TextComponentTranslation("pollution.machine.essence_collector_fluxthischunk", this.fluxThisChunk)).setStyle((new Style()).setColor(TextFormatting.RED)));
		textList.add((new TextComponentTranslation("pollution.machine.essence_collector_coillevel", this.coilLevel)).setStyle((new Style()).setColor(TextFormatting.RED)));
		textList.add((new TextComponentTranslation("pollution.machine.essence_collector_finalspeedpertick", this.finalSpeedPerTick)).setStyle((new Style()).setColor(TextFormatting.RED)));
		textList.add((new TextComponentTranslation("pollution.machine.essence_collector_euttier", this.EUtTier)).setStyle((new Style()).setColor(TextFormatting.RED)));
		if (isFocused) {
			textList.add((new TextComponentTranslation("pollution.machine.essence_collector_iffocused", "开启")).setStyle((new Style()).setColor(TextFormatting.RED)));
		} else {
			textList.add((new TextComponentTranslation("pollution.machine.essence_collector_iffocused", "关闭")).setStyle((new Style()).setColor(TextFormatting.RED)));
		}
	}

	int RadomTime = 0;

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

	@Override
	@SideOnly(Side.CLIENT)
	public void renderBloomEffect(BufferBuilder buffer, EffectRenderContext context) {
		int color = RenderUtil.interpolateColor(this.getFusionRingColor(), -1, Eases.QUAD_IN.getInterpolation(
				Math.abs((Math.abs(getOffsetTimer() % 50) + context.partialTicks()) - 25) / 25));


		float a = (float) (color >> 24 & 255) / 255.0F;
		float r = (float) (color >> 16 & 255) / 255.0F;
		float g = (float) (color >> 8 & 255) / 255.0F;
		float b = (float) (color & 255) / 255.0F;
		EnumFacing relativeBack = RelativeDirection.BACK.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
				isFlipped());
		EnumFacing.Axis axis = RelativeDirection.UP.getRelativeFacing(getFrontFacing(), getUpwardsFacing(), isFlipped())
				.getAxis();

		if (this.isWorkingEnabled() && this.isActive()) {
			RenderBufferHelper.renderRing(buffer,
					getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 6 + 0.5,
					getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 4.5,
					getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 6 + 0.5,
					3, 0.2, 10, 20,
					r, g, b, a, EnumFacing.Axis.Y);

			RenderBufferHelper.renderRing(buffer,
					getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 6 + 0.5,
					getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 4.5,
					getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 6 + 0.5,
					1, 1, 10, 20,
					r, g, b, a, axis);

			RenderBufferHelper.renderRing(buffer,
					getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 6 + 0.5,
					getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 8.5,
					getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 6 + 0.5,
					1, 0.1, 10, 20,
					r, g, b, a, axis);

			RenderBufferHelper.renderRing(buffer,
					getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 6 + 0.5,
					getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 7.5,
					getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 6 + 0.5,
					1, 0.1, 10, 20,
					r, g, b, a, axis);

			RenderBufferHelper.renderRing(buffer,
					getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 6 + 0.5,
					getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 1.5,
					getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 6 + 0.5,
					1, 0.1, 10, 20,
					r, g, b, a, axis);

			RenderBufferHelper.renderRing(buffer,
					getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 6 + 0.5,
					getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 0.5,
					getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 6 + 0.5,
					1, 0.1, 10, 20,
					r, g, b, a, axis);


			RenderBufferHelper.renderRing(buffer,
					getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 6 + 0.5,
					getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 4.5,
					getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 6 + 0.5,
					1, 1, 10, 20,
					r, g, b, a, EnumFacing.Axis.X);

			RenderBufferHelper.renderRing(buffer,
					getPos().getX() - context.cameraX() + relativeBack.getXOffset() * 6 + 0.5,
					getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 4.5,
					getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 6 + 0.5,
					1, 1, 10, 20,
					r, g, b, a, EnumFacing.Axis.Z);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRenderBloomEffect(EffectRenderContext context) {
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
		public void preDraw(BufferBuilder buffer) {
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
		public void postDraw(BufferBuilder buffer) {
			Tessellator.getInstance().draw();

			GlStateManager.enableTexture2D();
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
		}
	}
}
