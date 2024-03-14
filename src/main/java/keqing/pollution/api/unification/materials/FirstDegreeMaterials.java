package keqing.pollution.api.unification.materials;

import gregtech.api.unification.material.Material;
import keqing.pollution.api.unification.PollutionMaterials;

import static gregtech.api.util.GTUtility.gregtechId;

public class FirstDegreeMaterials {
    public FirstDegreeMaterials() {
    }
    private static int startId = 18100;
    private static final int END_ID = startId + 300;
    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void register() {
        PollutionMaterials.infused_air= new Material.Builder(getMaterialsId(), gregtechId("infused_air"))
                .color(0xFEFE7D)
                .ore().gem().fluid()
                .build();
        PollutionMaterials.infused_fire= new Material.Builder(getMaterialsId(), gregtechId("infused_fire"))
                .color(0xFE3C01)
                .ore().gem().fluid()
                .build();
        PollutionMaterials.infused_water= new Material.Builder(getMaterialsId(), gregtechId("infused_water"))
                .color(0x0090FF)
                .ore().gem().fluid()
                .build();
        PollutionMaterials.infused_earth= new Material.Builder(getMaterialsId(), gregtechId("infused_earth"))
                .color(0x00A000)
                .ore().gem().fluid()
                .build();
        PollutionMaterials.infused_entropy= new Material.Builder(getMaterialsId(), gregtechId("infused_entropy"))
                .color(0x43435E)
                .ore().gem().fluid()
                .build();
        PollutionMaterials.infused_order= new Material.Builder(getMaterialsId(), gregtechId("infused_order"))
                .color(0xEECCFF)
                .ore().gem().fluid()
                .build();


    }
}
