package keqing.pollution.api.unification;

import gregtech.api.unification.material.Material;
import keqing.pollution.api.unification.materials.FirstDegreeMaterials;
import keqing.pollution.api.unification.materials.PollutionElementMaterials;
import keqing.pollution.api.unification.materials.CatalystMaterials;

public class PollutionMaterials {
    public static Material infused_air;
    public static Material infused_fire;
    public static Material infused_water;
    public static Material infused_earth;
    public static Material infused_entropy;
    public static Material infused_order;

    public static Material thaumium;
    public static Material syrmorite;
    public static Material octine;
    public static Material scabyst;
    public static Material valonite;
    public static Material thaummix;

    public static Material roughdraft;

    public PollutionMaterials() {}

    public static void register() {
        PollutionElementMaterials.register();
        FirstDegreeMaterials.register();
        CatalystMaterials.register();

    }
}
