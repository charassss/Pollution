package keqing.pollution.api.unification.materials;

import gregtech.api.unification.material.Material;
import keqing.pollution.api.unification.PollutionMaterials;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static keqing.pollution.api.unification.PollutionMaterials.*;

public class CatalystMaterials {

    public CatalystMaterials() {
    }
    private static int startId = 16400;
    private static final int END_ID = startId + 300;
    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }
    public static void register() {

        //活性催化粗胚，作为催化剂系列的起始物品
        PollutionMaterials.roughdraft = new Material.Builder(getMaterialsId(), gregtechId("roughdraft"))
                .dust()
                .color(0xCDA5F7)
                .iconSet(DULL)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(Amethyst, 1, Opal, 1, CertusQuartz, 1, scabyst, 1)
                .build();

        //魔力催化场基底，作为和神秘相关设备合成的催化剂基底物质
        PollutionMaterials.roughdraft = new Material.Builder(getMaterialsId(), gregtechId("roughdraft"))
                .dust()
                .color(0xCDA5F7)
                .iconSet(SHINY)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(roughdraft, 6 , infused_fire, 1, infused_air, 1, infused_entropy, 1)
                .build();

    }
}
