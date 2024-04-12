package keqing.pollution.client.tesr;

import keqing.pollution.common.block.BlockTileEntity.TileEntityMagicCircle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.obj.AdvancedModelLoader;
import thaumcraft.client.lib.obj.IModelCustom;

public class TesrMagicCircle extends TileEntitySpecialRenderer<TileEntityMagicCircle> {
    public static final ResourceLocation MAGIC_CIRCLE = new ResourceLocation("pollution", "models/magic_circle.png");
    public static final IModelCustom Magic_Model= AdvancedModelLoader.loadModel(new ResourceLocation("pollution", "models/magic_circle.obj"));
    @Override
    public void render(TileEntityMagicCircle te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        FMLClientHandler.instance().getClient().getTextureManager().bindTexture(MAGIC_CIRCLE);
        GlStateManager.translate(x,y,z);
        GlStateManager.translate(0.5,0.5,0.5);
        GL11.glRotatef((System.currentTimeMillis() / 64) % 360, 0F, 1F, 0F);
        final float scale = 2.5f;
        GL11.glScalef(scale, scale, scale);
        GL11.glColor4f(1, 1, 1, 1);
        Magic_Model.renderAll();
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
}
