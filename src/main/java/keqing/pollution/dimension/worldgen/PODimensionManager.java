package keqing.pollution.dimension.worldgen;

import keqing.pollution.dimension.dims.DimensionDemiplane;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.awt.geom.Dimension2D;

public class PODimensionManager {
    //这个是维度管理器，拿来注册的
    public static DimensionType DEMIPLANE;
    public static int DEMIPLANE_DIM_ID;

    public static void init() {
        // 这里注册你的维度ID和维度类型
        DEMIPLANE_DIM_ID = 527;
        DimensionManager.registerDimension(DEMIPLANE_DIM_ID, DimensionType.register("the_demiplane", "_demiplane", DEMIPLANE_DIM_ID, DimensionDemiplane.class, false));
    }
}
