package xieao.theora.client.renderer.tesr;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import xieao.theora.Theora;
import xieao.theora.client.helper.RendererHelper;
import xieao.theora.common.block.bindingstone.TileBindingRing;

public class RenderBindingRing extends TESRBase<TileBindingRing> {

    @Override
    public void render(TileBindingRing te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0.5D, 0.0002D, 0.5D);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        mc.getTextureManager().bindTexture(Theora.location("textures/tesr/bindingring.png"));
        RendererHelper.renderFlatQuad(1.0F);
        GlStateManager.disableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(0.0D, 0.4D, 0.0D);
        RendererHelper.renderItemStack(te.getStackInSlot(0), 1.0F);
        GlStateManager.popMatrix();
    }
}
