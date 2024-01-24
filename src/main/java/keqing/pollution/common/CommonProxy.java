package keqing.pollution.common;


import gregtech.common.items.MetaItems;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;
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

    public void preInit( FMLPreInitializationEvent event ) {

    }
    public static void init() {

        recipes.init();
    }

    public CommonProxy() {
    }

    public void preLoad() {

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {


    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {

    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
        return itemBlock;
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {

    }
}
