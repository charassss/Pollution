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

        //六种神秘gcym用材料
        PollutionMaterials.aertitanium = new Material.Builder(getMaterialsId(), gregtechId("aertitanium"))
                .color(0xEED2EE)
                .ingot().fluid()
                .components(Titanium, 2, Aluminium, 1, Manganese, 1, infused_air, 5)
                .iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_ROTOR, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, DECOMPOSITION_BY_CENTRIFUGING)
                .build()
                .setFormula("Ti2AlMnAe5", true);

        PollutionMaterials.ignissteel = new Material.Builder(getMaterialsId(), gregtechId("ignissteel"))
                .color(0x8B1A1A)
                .ingot().fluid()
                .components(Steel, 2, Neodymium, 1, Magnesium, 1, infused_fire, 5)
                .iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_ROTOR, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, DECOMPOSITION_BY_CENTRIFUGING)
                .build()
                .setFormula("Fe2NdMgIg5", true);

        PollutionMaterials.aquasilver = new Material.Builder(getMaterialsId(), gregtechId("aquasilver"))
                .color(0xCAE1FF)
                .ingot().fluid()
                .components(Silver, 2, Tin, 1, Mercury, 1, infused_water, 5)
                .iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_ROTOR, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, DECOMPOSITION_BY_CENTRIFUGING)
                .build()
                .setFormula("Ag2SnHgAq5", true);

        PollutionMaterials.terracopper = new Material.Builder(getMaterialsId(), gregtechId("terracopper"))
                .color(0x8FBC8F)
                .ingot().fluid()
                .components(Copper, 2, Silicon, 1, Carbon, 1, infused_earth, 5)
                .iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_ROTOR, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, DECOMPOSITION_BY_CENTRIFUGING)
                .build()
                .setFormula("Cu2SiCTer5", true);

        PollutionMaterials.ordolead = new Material.Builder(getMaterialsId(), gregtechId("ordolead"))
                .color(0x00008B)
                .ingot().fluid()
                .components(Lead, 2, Helium, 1, Gold, 1, infused_order, 5)
                .iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_ROTOR, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, DECOMPOSITION_BY_CENTRIFUGING)
                .build()
                .setFormula("Pb2HeAuOrd5", true);

        PollutionMaterials.perditioaluminium = new Material.Builder(getMaterialsId(), gregtechId("perditioaluminium"))
                .color(0x9C9C9C)
                .ingot().fluid()
                .components(Aluminium, 2, Fluorine, 1, Uraninite, 1, infused_entropy, 5)
                .iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_ROTOR, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROUND, DECOMPOSITION_BY_CENTRIFUGING)
                .build()
                .setFormula("Al2FUPer5", true);

        //牢大 想你了
        PollutionMaterials.kobemetal = new Material.Builder(getMaterialsId(), gregtechId("kobemetal"))
                .color(0xFFD700)
                .ingot().fluid()
                .components(Helium, 1, Lithium, 1, Cobalt, 1, Platinum, 1, Erbium, 1)
                .iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_ROD, DECOMPOSITION_BY_CENTRIFUGING)
                .build()
                .setFormula("HeLiCoPtEr", true);

        //注释
        //在 Elements 类下注册元素
        //在 PollutionElementMaterials 类下注册此元素的单质
        //在 FirstDegreeMaterials 类下注册元素的化合物（components使用单质而不是元素）
    }
}
