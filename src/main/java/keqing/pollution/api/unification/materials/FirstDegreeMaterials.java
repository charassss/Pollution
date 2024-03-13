package keqing.pollution.api.unification.materials;

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
        /* 例子
        GTQTMaterials.RedstoneAlloy = new Material.Builder(getMaterialsId(), gregtechId("redstone_alloy"))
                .ingot().fluid()
                .color(0x943423).iconSet(SHINY)
                .flags(GENERATE_PLATE, GENERATE_DOUBLE_PLATE,GENERATE_DENSE,GENERATE_LONG_ROD,GENERATE_ROD,GENERATE_FRAME,GENERATE_BOLT_SCREW,GENERATE_FOIL,GENERATE_GEAR,GENERATE_SMALL_GEAR,GENERATE_ROUND,GENERATE_SPRING)
                .components(Redstone ,1,Hydrogen, 1,Oxygen ,1)
                .blast(2700,LOW)
                .cableProperties(32,1,2)
                .build();
         */


    }
}
