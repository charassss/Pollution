package keqing.pollution.common.metatileentity;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import keqing.pollution.Pollution;
import net.minecraft.util.ResourceLocation;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;

public class PollutionMetaTileEntities {
    public static ResourceLocation gtqtcoreId(String id) {
        return new ResourceLocation(Pollution.MODID, id);
    }
    public static final MetaTileEntityMultiblockPart[] FLUX_MUFFLERS = new MetaTileEntityMultiblockPart[9];
    public static final TieredMetaTileEntity[] AURA_GENERATORS = new TieredMetaTileEntity[3];
    public static void initialization() {

        for (int i = 0; i <= 2; i++) {
            String tierName = GTValues.VN[i+1].toLowerCase();
            AURA_GENERATORS[i] = registerMetaTileEntity(15900+i-1 ,new MetaTileEntityVisGenerator(gtqtcoreId("vis." + tierName), i+1));
        }

        for (int i = 1; i <= 8; i++) {
            String tierName = GTValues.VN[i].toLowerCase();
            FLUX_MUFFLERS[i] = registerMetaTileEntity(15910 + i-1, new MetaTileEntityFluxMuffler(gtqtcoreId("pollution_muffler_hatch." + tierName), i));
        }
    }
}
