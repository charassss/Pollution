package keqing.pollution.api.unification;

import gregtech.api.unification.material.Material;
import keqing.pollution.api.unification.materials.FirstDegreeMaterials;

public class PollutionMaterials {
    public static Material infused_air;
    public static Material infused_fire;
    public static Material infused_water;
    public static Material infused_earth;
    public static Material infused_entropy;
    public static Material infused_order;

    public PollutionMaterials() {}

    public static void register() {

        FirstDegreeMaterials.register();

    }
}
