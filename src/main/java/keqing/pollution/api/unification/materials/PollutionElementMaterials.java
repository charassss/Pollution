package keqing.pollution.api.unification.materials;

import gregtech.api.unification.material.Material;
import keqing.pollution.api.unification.Elements;
import keqing.pollution.api.unification.PollutionMaterials;

import static gregtech.api.util.GTUtility.gregtechId;

public class PollutionElementMaterials {
    private static int startId = 18000;
    private static final int END_ID = startId + 100;
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
                .element(Elements.Ai)
                .build();
        PollutionMaterials.infused_fire= new Material.Builder(getMaterialsId(), gregtechId("infused_fire"))
                .color(0xFE3C01)
                .ore().gem().fluid()
                .element(Elements.Fi)
                .build();
        PollutionMaterials.infused_water= new Material.Builder(getMaterialsId(), gregtechId("infused_water"))
                .color(0x0090FF)
                .ore().gem().fluid()
                .element(Elements.Wa)
                .build();
        PollutionMaterials.infused_earth= new Material.Builder(getMaterialsId(), gregtechId("infused_earth"))
                .color(0x00A000)
                .ore().gem().fluid()
                .element(Elements.Ea)
                .build();
        PollutionMaterials.infused_entropy= new Material.Builder(getMaterialsId(), gregtechId("infused_entropy"))
                .color(0x43435E)
                .ore().gem().fluid()
                .element(Elements.En)
                .build();
        PollutionMaterials.infused_order= new Material.Builder(getMaterialsId(), gregtechId("infused_order"))
                .color(0xEECCFF)
                .ore().gem().fluid()
                .element(Elements.Ord)
                .build();


    }
}
