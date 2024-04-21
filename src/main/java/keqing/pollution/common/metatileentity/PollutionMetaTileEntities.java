package keqing.pollution.common.metatileentity;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import keqing.pollution.Pollution;
import keqing.pollution.common.metatileentity.multiblock.MetaTileEntityFluxClear;
import keqing.pollution.common.metatileentity.multiblock.*;
import keqing.pollution.common.metatileentity.multiblock.MetaTileEntityMagicBender;
import keqing.pollution.common.metatileentity.multiblock.MetaTileEntityTankTest;
import keqing.pollution.common.metatileentity.multiblockpart.MetaTileEntityFluxMuffler;
import keqing.pollution.common.metatileentity.multiblockpart.MetaTileEntityTankHatch;
import keqing.pollution.common.metatileentity.multiblockpart.MetaTileEntityVisHatch;
import keqing.pollution.common.metatileentity.single.MetaTileEntitySolarPlate;
import keqing.pollution.common.metatileentity.single.MetaTileEntityVisClear;
import keqing.pollution.common.metatileentity.single.MetaTileEntityVisGenerator;
import keqing.pollution.common.metatileentity.single.MetaTileEntityVisProvider;
import net.minecraft.util.ResourceLocation;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static keqing.pollution.client.textures.POTextures.*;

public class PollutionMetaTileEntities {
    public static ResourceLocation gtqtcoreId(String id) {
        return new ResourceLocation(Pollution.MODID, id);
    }
    public static final MetaTileEntityMultiblockPart[] FLUX_MUFFLERS = new MetaTileEntityMultiblockPart[9];
    public static final TieredMetaTileEntity[] AURA_GENERATORS = new TieredMetaTileEntity[6];
    public static final MetaTileEntityVisProvider[] VIS_PROVIDERS = new MetaTileEntityVisProvider[9];
    public static final MetaTileEntityVisClear[] VIS_CLEAR = new MetaTileEntityVisClear[4];
    public static final MetaTileEntityFluxClear[] FLUX_CLEARS = new MetaTileEntityFluxClear[3];

    public static MetaTileEntityTankTest TANK;

    public static MetaTileEntityTankTest TANK_TEST;

    public static MetaTileEntityMagicBender MAGIC_BENDER;
    public static MetaTileEntityMagicCentrifuge MAGIC_CENTRIFUGE;
    public static MetaTileEntityMagicElectricBlastFurnace MAGIC_ELECTRIC_BLAST_FURNACE;
    public static MetaTileEntityMagicElectrolyzer MAGIC_ELECTROLYZER;
    public static MetaTileEntityMagicMixer MAGIC_MIXER;
    public static MetaTileEntityMagicMacerator MAGIC_MACERATOR;
    public static MetaTileEntityMagicChemicalBath MAGIC_CHEMICAL_BATH;
    public static MetaTileEntityMagicSifter MAGIC_SIFTER;
    public static MetaTileEntityMagicCutter MAGIC_CUTTER;
    public static MetaTileEntityMagicWireMill MAGIC_WIREMILL;
    public static MetaTileEntityMagicSolidifier MAGIC_SOLIDIFIER;
    public static MetaTileEntityMagicBrewery MAGIC_BREWERY;
    public static MetaTileEntityMagicBattery MAGIC_BATTERY;
    public static MetaTileEntityMagicGreenHouse MAGIC_GREEN_HOUSE;
    public static MetaTileEntityMagicDistillery MAGIC_DISTILLERY;
    public static MetaTileEntityMagicAlloyBlastSmelter MAGIC_ALLOY_BLAST;
    public static MetaTileEntityVisHatch[] VIS_HATCH = new MetaTileEntityVisHatch[14];
    public static MetaTileEntityTankHatch[] TANK_HATCH = new MetaTileEntityTankHatch[1];

    public static MetaTileEntitySolarPlate[] SOLAR_PLATE = new MetaTileEntitySolarPlate[18];
    public static void initialization() {

        for (int i = 0; i <= 4; i++) {
            String tierName = GTValues.VN[i + 1].toLowerCase();
            AURA_GENERATORS[i] = registerMetaTileEntity(15900 + i - 1, new MetaTileEntityVisGenerator(gtqtcoreId("vis." + tierName), i + 1));
        }

        for (int i = 0; i <= 7; i++) {
            String tierName = GTValues.VN[i + 1].toLowerCase();
            VIS_PROVIDERS[i] = registerMetaTileEntity(15920 + i - 1, new MetaTileEntityVisProvider(gtqtcoreId("vis_provider." + tierName), i + 1));
        }

        for (int i = 0; i <= 2; i++) {
            String tierName = GTValues.VN[i + 1].toLowerCase();
            VIS_CLEAR[i] = registerMetaTileEntity(15930 + i - 1, new MetaTileEntityVisClear(gtqtcoreId("flux_clear." + tierName), i + 1));
        }

        FLUX_CLEARS[1] = registerMetaTileEntity(15933, new MetaTileEntityFluxClear(gtqtcoreId("flux_clear.ev"), GTValues.EV));
        FLUX_CLEARS[2] = registerMetaTileEntity(15934, new MetaTileEntityFluxClear(gtqtcoreId("flux_clear.iv"), GTValues.IV));
        TANK = registerMetaTileEntity(15935, new MetaTileEntityTankTest(gtqtcoreId("TANK")));

        MAGIC_BENDER = registerMetaTileEntity(15936, new MetaTileEntityMagicBender(gtqtcoreId("magic_bender")));
        MAGIC_ELECTRIC_BLAST_FURNACE = registerMetaTileEntity(15937, new MetaTileEntityMagicElectricBlastFurnace(gtqtcoreId("magic_electric_blast_furnace")));
        MAGIC_CENTRIFUGE = registerMetaTileEntity(15938, new MetaTileEntityMagicCentrifuge(gtqtcoreId("magic_centrifuge")));
        MAGIC_ELECTROLYZER = registerMetaTileEntity(15939, new MetaTileEntityMagicElectrolyzer(gtqtcoreId("magic_electrolyzer")));
        MAGIC_MIXER = registerMetaTileEntity(15940, new MetaTileEntityMagicMixer(gtqtcoreId("magic_mixer")));
        MAGIC_MACERATOR = registerMetaTileEntity(15941, new MetaTileEntityMagicMacerator(gtqtcoreId("magic_macerator")));
        MAGIC_CHEMICAL_BATH = registerMetaTileEntity(15942, new MetaTileEntityMagicChemicalBath(gtqtcoreId("magic_chemical_bath")));
        MAGIC_SIFTER = registerMetaTileEntity(15943, new MetaTileEntityMagicSifter(gtqtcoreId("magic_sifter")));
        MAGIC_CUTTER = registerMetaTileEntity(15944, new MetaTileEntityMagicCutter(gtqtcoreId("magic_cutter")));
        MAGIC_WIREMILL = registerMetaTileEntity(15945, new MetaTileEntityMagicWireMill(gtqtcoreId("magic_wiremill")));
        MAGIC_SOLIDIFIER = registerMetaTileEntity(15946, new MetaTileEntityMagicSolidifier(gtqtcoreId("magic_solidifier")));
        MAGIC_BREWERY = registerMetaTileEntity(15947, new MetaTileEntityMagicBrewery(gtqtcoreId("magic_brewery")));
        MAGIC_DISTILLERY = registerMetaTileEntity(15948, new MetaTileEntityMagicDistillery(gtqtcoreId("magic_distillery")));
        MAGIC_BATTERY = registerMetaTileEntity(15950, new MetaTileEntityMagicBattery(gtqtcoreId("magic_battery")));
        MAGIC_ALLOY_BLAST = registerMetaTileEntity(15951, new MetaTileEntityMagicAlloyBlastSmelter(gtqtcoreId("magic_alloy_blast")));
        MAGIC_GREEN_HOUSE = registerMetaTileEntity(15952, new MetaTileEntityMagicGreenHouse(gtqtcoreId("magic_green_house")));
        for (int i = 1; i <= 8; i++) {
            String tierName = GTValues.VN[i].toLowerCase();
            FLUX_MUFFLERS[i] = registerMetaTileEntity(16000 + i - 1, new MetaTileEntityFluxMuffler(gtqtcoreId("pollution_muffler_hatch." + tierName), i));
        }

        for (int i = 0; i < VIS_HATCH.length; i++) {
            int tier = GTValues.LV + i;
            VIS_HATCH[i] = registerMetaTileEntity(16020 + i, new MetaTileEntityVisHatch(
                    gtqtcoreId(String.format("vis_hatch.%s", GTValues.VN[tier])), tier));
        }

        for (int i = 0; i < TANK_HATCH.length; i++) {
            int tier = GTValues.LV + i;
            TANK_HATCH[i] = registerMetaTileEntity(16040 + i, new MetaTileEntityTankHatch(
                    gtqtcoreId(String.format("tank_hatch.%s", GTValues.VN[tier])), tier));
        }

        int kind;
        for (kind = 1; kind <= 6; kind++) {
            SOLAR_PLATE[kind * 3 - 3] = registerMetaTileEntity(16060 + kind * 3 - 3, new MetaTileEntitySolarPlate(
                    gtqtcoreId(String.format("solar_plate_%s.%s", 1, kind)), 1, kind, SOLAR_PLATE_I));
            SOLAR_PLATE[kind * 3 - 2] = registerMetaTileEntity(16060 + kind * 3 - 2, new MetaTileEntitySolarPlate(
                    gtqtcoreId(String.format("solar_plate_%s.%s", 2, kind)), 2, kind, SOLAR_PLATE_II));
            SOLAR_PLATE[kind * 3 - 1] = registerMetaTileEntity(16060 + kind * 3 - 1, new MetaTileEntitySolarPlate(
                    gtqtcoreId(String.format("solar_plate_%s.%s", 3, kind)), 3, kind, SOLAR_PLATE_III));

        }
    }
}
