package keqing.pollution;

import keqing.pollution.common.metatileentity.PollutionMetaTileEntities;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;
import java.util.List;

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

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

        PollutionMetaTileEntities.initialization();


    }

}