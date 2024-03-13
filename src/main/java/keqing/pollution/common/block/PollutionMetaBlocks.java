package keqing.pollution.common.block;

import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PollutionMetaBlocks {

    private PollutionMetaBlocks() {}

    public static void init() {}
    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {}
    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Block block) {}
}
