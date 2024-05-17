package keqing.pollution;
import net.minecraftforge.common.config.Config;
@Config(modid = Pollution.MODID)
public class POConfig {
    public static float mufflerPollutionMultiplier =0.02f;
    public static boolean mufflerPollutionShowEffects=false;
    public static double fluxScrubberMultiplier=0.004;
    public static float visGeneratorEuPerVis=250.0f;
    public static float visGeneratorPollutionMultiplier=0.1f;
    public static float visGeneratorMinPollution=0.0f;
    public static float visGeneratorMaxPollution=1.0f;
    public static boolean visGeneratorPollutionShowEffects =false;
    public static double visProviderMultiplier=0.05;
}
