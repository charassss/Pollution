package keqing.pollution.common.block;

import gregtech.common.blocks.MetaBlocks;
import keqing.pollution.common.block.PollutionMetaBlock.POMagicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PollutionMetaBlocks {
    public static POMagicBlock MAGIC_BLOCK;
    private PollutionMetaBlocks() {}
    public static void init() {
        MAGIC_BLOCK = new POMagicBlock();
        MAGIC_BLOCK.setRegistryName("magic_block");
    }
    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        registerItemModel(MAGIC_BLOCK);

    }
    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Block block) {
        for (IBlockState state : block.getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),
                    block.getMetaFromState(state),
                    new ModelResourceLocation(block.getRegistryName(),
                            MetaBlocks.statePropertiesToString(state.getProperties())));
        }
    }
}