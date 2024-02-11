package keqing.pollution.mixin;

import gregtech.api.metatileentity.multiblock.RecipeMapPrimitiveMultiblockController;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.GTUtility;
import gregtech.client.particle.VanillaParticleEffects;
import gregtech.common.metatileentities.multi.MetaTileEntityCokeOven;
import gregtech.common.metatileentities.multi.MetaTileEntityPrimitiveBlastFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import thaumcraft.api.aura.AuraHelper;

import java.util.List;

@Mixin(MetaTileEntityCokeOven.class)
public abstract class MixinMetaTileEntityCokeOven extends RecipeMapPrimitiveMultiblockController {

    public MixinMetaTileEntityCokeOven(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.COKE_OVEN_RECIPES);
    }


    @Override
    public void update() {
        super.update();

        if (this.isActive()) {
            AuraHelper.polluteAura(getWorld(), getPos(), 0.00004f, true);
        }
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("pollution.vis.product",0.0008));

    }

}
