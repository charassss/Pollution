package keqing.pollution.api.unification.materials;

import gregtech.api.unification.material.Material;
import keqing.pollution.api.unification.Elements;
import keqing.pollution.api.unification.PollutionMaterials;

import static gregtech.api.util.GTUtility.gregtechId;

public class CompoundAspectMaterials {
    public CompoundAspectMaterials() {
    }
    private static int startId = 16200;
    private static final int END_ID = startId + 300;
    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void register() {
        //水晶=风+地
        PollutionMaterials.infused_crystal= new Material.Builder(getMaterialsId(), gregtechId("infused_crystal"))
                .color(0x87CEFA)
                .ore().gem().fluid()
                .components(PollutionMaterials.infused_air, 1, PollutionMaterials.infused_earth, 1)
                .build();
    }
}
