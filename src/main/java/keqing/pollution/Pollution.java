package keqing.pollution;

import keqing.pollution.api.utils.PollutionLog;
import keqing.pollution.client.ClientProxy;
import keqing.pollution.common.CommonProxy;
import keqing.pollution.common.block.PollutionMetaBlocks;
import keqing.pollution.common.metatileentity.PollutionMetaTileEntities;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = "pollution",
        name="Pollution",
        acceptedMinecraftVersions = "[1.12.2,1.13)",
        version = "0.0.1-beta" ,
        dependencies = "required-after:gregtech@[2.8.5-beta,) ;"
)
public class Pollution  {
    public static final String MODID = "pollution";
    public static final String NAME = "Pollution";
    public static final String VERSION = "1.0";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static CommonProxy proxy;
    public static ClientProxy cproxy;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        CommonProxy.init();
    }
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        PollutionLog.init(event.getModLog());
        PollutionMetaTileEntities.initialization();
        PollutionMetaBlocks.init();
    }

}