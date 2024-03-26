package keqing.pollution.api.unification.materials;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.ToolProperty;
import keqing.pollution.api.unification.PollutionMaterials;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static keqing.pollution.api.unification.PollutionMaterials.*;


public class FirstDegreeMaterials {
    public FirstDegreeMaterials() {
    }
    private static int startId = 16100;
    private static final int END_ID = startId + 300;
    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static void register() {
        PollutionMaterials.thaumium = new Material.Builder(getMaterialsId(), gregtechId("thaumium"))
                .color(0x483D8B)
                .ingot().fluid()
                .components(Iron, 1, infused_earth, 5, infused_air, 5, infused_fire, 5, infused_order, 5)
                .fluidPipeProperties(500, 120, true)
                .toolStats(new ToolProperty(10, 4, 1024, 3))
                .iconSet(BRIGHT)
                .flags(GENERATE_PLATE, GENERATE_ROTOR, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, DECOMPOSITION_BY_CENTRIFUGING)
                .build()
                .setFormula("FeTer5(AeIgOrd)5", true);

        PollutionMaterials.syrmorite = new Material.Builder(getMaterialsId(), gregtechId("syrmorite"))
                .color(0x2414B3)
                .ingot().fluid().ore()
                .components(Copper, 1, infused_earth, 10, infused_order, 5)
                .iconSet(METALLIC)
                .flags(GENERATE_PLATE, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROD, DECOMPOSITION_BY_CENTRIFUGING)
                .build()
                .setFormula("CuTer5(TerOrd)5", true);

        PollutionMaterials.octine = new Material.Builder(getMaterialsId(), gregtechId("octine"))
                .color(0xFFAE33)
                .ingot().fluid().ore()
                .components(Iron, 1, infused_fire, 10, infused_air, 5)
                .iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROD, DECOMPOSITION_BY_CENTRIFUGING)
                .build()
                .setFormula("FeIg5(AeIg)5", true);

        PollutionMaterials.scabyst = new Material.Builder(getMaterialsId(), gregtechId("scabyst"))
                .color(0x53C58D)
                .gem().fluid().ore()
                .components(Iron, 1, Silicon, 4, Oxygen, 8, infused_fire, 5, infused_earth, 5, infused_order, 10)
                .iconSet(GEM_HORIZONTAL)
                .flags(GENERATE_PLATE, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROD, DECOMPOSITION_BY_CENTRIFUGING)
                .build()
                .setFormula("((SiO2)4Fe)(IgTerOrd2)5)4", true);

        PollutionMaterials.valonite = new Material.Builder(getMaterialsId(), gregtechId("valonite"))
                .color(0xFFCCFF)
                .gem().fluid().ore()
                .iconSet(EMERALD)
                .flags(GENERATE_PLATE, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROD)
                .build()
                .setFormula("✴", true);

        PollutionMaterials.thaummix = new Material.Builder(getMaterialsId(), gregtechId("thaummix"))
                .color(0x5900B3)
                .dust()
                .iconSet(SHINY)
                .build()
                .setFormula("(FeIg5(AeIg)5)(CuTer5(TerOrd)5)", true);

        //注释
        //在 Elements 类下注册元素
        //在 PollutionElementMaterials 类下注册此元素的单质
        //在 FirstDegreeMaterials 类下注册元素的化合物（components使用单质而不是元素）
    }
}
