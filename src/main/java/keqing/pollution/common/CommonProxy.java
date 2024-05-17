package keqing.pollution.common;


import gregtech.api.block.VariantItemBlock;
import gregtech.common.items.MetaItems;

import keqing.pollution.api.utils.PollutionLog;
import keqing.pollution.common.block.PollutionMetaBlocks;
import keqing.pollution.dimension.worldgen.PODimensionType;
import keqing.pollution.loaders.RecipeManger;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Objects;
import java.util.Random;
import java.util.function.Function;


@Mod.EventBusSubscriber(
        modid = "pollution"
)
public class CommonProxy {

    public static final CreativeTabs Pollution_TAB = new CreativeTabs("Pollution") {
        @Override
        public ItemStack createIcon() {
            return MetaItems.WETWARE_MAINFRAME_UHV.getStackForm();
        }
    };


    public void init() {
        RecipeManger.init();
    }

    public void preLoad(){

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        PollutionLog.logger.info("Registering blocks...");
        IForgeRegistry<Block> registry = event.getRegistry();
        /*
        在此处注册方块
        例子：
        registry.register(方块实例);
        在注册MetaBlock时用到
        */
        registry.register(PollutionMetaBlocks.MAGIC_BLOCK);
        registry.register(PollutionMetaBlocks.TURBINE);
        registry.register(PollutionMetaBlocks.BEAM_CORE);
        registry.register(PollutionMetaBlocks.WIRE_COIL);
        registry.register(PollutionMetaBlocks.FUSION_REACTOR);
        registry.register(PollutionMetaBlocks.GLASS);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        PollutionLog.logger.info("Registering Items...");
        IForgeRegistry<Item> registry = event.getRegistry();
        /*
        在此处注册方块的物品
        例子：
        registry.register(createItemBlock(方块实例, VariantItemBlock::new));
        在注册MetaBlock时用到
        */
        registry.register(createItemBlock(PollutionMetaBlocks.MAGIC_BLOCK, VariantItemBlock::new));
        registry.register(createItemBlock(PollutionMetaBlocks.TURBINE, VariantItemBlock::new));
        registry.register(createItemBlock(PollutionMetaBlocks.BEAM_CORE, VariantItemBlock::new));
        registry.register(createItemBlock(PollutionMetaBlocks.WIRE_COIL, VariantItemBlock::new));
        registry.register(createItemBlock(PollutionMetaBlocks.FUSION_REACTOR, VariantItemBlock::new));
        registry.register(createItemBlock(PollutionMetaBlocks.GLASS, VariantItemBlock::new));
    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
        return itemBlock;
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        PollutionLog.logger.info("Registering recipes...");
    }
}
