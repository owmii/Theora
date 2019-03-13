package xieao.theora.client.renderer.tile;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.ResourceLocation;
import xieao.theora.api.Consts;
import xieao.theora.block.hor.TileHor;
import xieao.theora.client.model.ModelHor;

public class RenderHor extends TileEntityRenderer<TileHor> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Consts.MOD_ID, "textures/ter/hor.png");
    private static final ModelHor MODEL_HOR = new ModelHor();

    @Override
    public void render(TileHor hor, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.translated(x, y, z);
        GlStateManager.scalef(1.0F, -1.0F, -1.0F);
        GlStateManager.translated(0.5D, -2.5D, -1.5D);
        bindTexture(TEXTURE);
        MODEL_HOR.render();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }

    @Override
    public boolean isGlobalRenderer(TileHor te) {
        return true;
    }
}
