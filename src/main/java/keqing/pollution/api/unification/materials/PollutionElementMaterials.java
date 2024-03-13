package keqing.pollution.api.unification.materials;

public class PollutionElementMaterials {
    private static int startId = 18000;
    private static final int END_ID = startId + 100;
    private static int getMaterialsId() {
        if (startId < END_ID) {
            return startId++;
        }
        throw new ArrayIndexOutOfBoundsException();
    }
}
