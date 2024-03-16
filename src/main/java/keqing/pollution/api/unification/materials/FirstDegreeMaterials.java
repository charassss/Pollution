package keqing.pollution.api.unification.materials;

import gregtech.api.unification.material.Material;
import keqing.pollution.api.unification.Elements;
import keqing.pollution.api.unification.PollutionMaterials;

import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.METALLIC;
import static gregtech.api.util.GTUtility.gregtechId;
import static keqing.pollution.api.unification.PollutionMaterials.*;


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
        PollutionMaterials.MxeteoricIron = new Material.Builder(getMaterialsId(), gregtechId("mxeteoric_iron"))
                .ingot().dust().ore()
                .color(0x8B6914).iconSet(METALLIC)
                .components(infused_air,1,infused_fire,1)
                .flags(GENERATE_PLATE, GENERATE_DENSE,GENERATE_DOUBLE_PLATE, GENERATE_ROD, GENERATE_LONG_ROD, GENERATE_RING, GENERATE_ROUND, GENERATE_BOLT_SCREW, GENERATE_FRAME, GENERATE_GEAR, GENERATE_SMALL_GEAR)
                .build();

        //注释
        //在 Elements 类下注册元素
        //在 PollutionElementMaterials 类下注册此元素的单质
        //在 FirstDegreeMaterials 类下注册元素的化合物（components使用单质而不是元素）
    }
}
