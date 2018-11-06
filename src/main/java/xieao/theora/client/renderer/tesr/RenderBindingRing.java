package xieao.theora.client.renderer.tesr;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import xieao.theora.Theora;
import xieao.theora.client.helper.ColorHelper;
import xieao.theora.client.helper.RendererHelper;
import xieao.theora.common.block.bindingstone.TileBindingRing;

public class RenderBindingRing extends TESRBase<TileBindingRing> {

    @Override
    public void render(TileBindingRing te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        float ticks = RendererHelper.tickCount + partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0.5D, 0.0002D, 0.5D);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        ColorHelper.glColor(te.isEmpty() ? 0xffffff : 0xffffff, 0.3F);
        RendererHelper.renderQuad(Theora.location("textures/tesr/bindingring.png"), 1.0F);
        GlStateManager.disableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(0.0D, 0.4D, 0.0D);
        GlStateManager.rotate(-ticks * 2.5F, 0, 1, 0);
        RendererHelper.renderItemStack(te.getStackInSlot(0), 1.0F);
        GlStateManager.popMatrix();
    }
}
