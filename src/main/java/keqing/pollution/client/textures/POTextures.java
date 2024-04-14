package keqing.pollution.client.textures;

import codechicken.lib.texture.TextureUtils;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class POTextures {
    public static SimpleOverlayRenderer SPELL_PRISM;
    public static SimpleOverlayRenderer SPELL_PRISM_COLD;
    public static SimpleOverlayRenderer SPELL_PRISM_HOT;
    public static SimpleOverlayRenderer SPELL_PRISM_WATER;
    public static SimpleOverlayRenderer SPELL_PRISM_AIR;
    public static SimpleOverlayRenderer SPELL_PRISM_VOID;
    public static SimpleOverlayRenderer SPELL_PRISM_ORDER;
    public static SimpleOverlayRenderer SPELL_PRISM_EARTH;
    public static SimpleOverlayRenderer VOID_PRISM;
    public static void init() {
        SPELL_PRISM = new SimpleOverlayRenderer("magicblock/spell_prism");
        SPELL_PRISM_COLD = new SimpleOverlayRenderer("magicblock/spell_prism_cold");
        SPELL_PRISM_HOT = new SimpleOverlayRenderer("magicblock/spell_prism_hot");
        SPELL_PRISM_AIR = new SimpleOverlayRenderer("magicblock/spell_prism_air");
        SPELL_PRISM_VOID = new SimpleOverlayRenderer("magicblock/spell_prism_void");
        SPELL_PRISM_WATER = new SimpleOverlayRenderer("magicblock/spell_prism_water");
        SPELL_PRISM_ORDER = new SimpleOverlayRenderer("magicblock/spell_prism_order");
        SPELL_PRISM_EARTH = new SimpleOverlayRenderer("magicblock/spell_prism_earth");
        VOID_PRISM = new SimpleOverlayRenderer("magicblock/void_prism");

    }
    public static void register(TextureMap textureMap) {

    }
    public static void preInit() {
        TextureUtils.addIconRegister(POTextures::register);
    }
}