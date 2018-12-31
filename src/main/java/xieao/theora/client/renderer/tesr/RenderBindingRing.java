package xieao.theora.client.renderer.tesr;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.lib.util.ColorUtil;
import xieao.lib.util.RenderUtil;
import xieao.theora.Theora;
import xieao.theora.common.block.binding.TileBindingRing;

@SideOnly(Side.CLIENT)
public class RenderBindingRing extends TESRBase<TileBindingRing> {

    public static final ResourceLocation RING_TEXTURE = Theora.location("textures/tesr/binding_ring.png");

    @Override
    public void render(TileBindingRing te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        float ticks = RenderUtil.tickCount + partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0.5D, 0.0002D, 0.5D);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        ColorUtil.glColor(0xffffff, 0.3F);
        RenderUtil.renderQuad(RING_TEXTURE, 1.0F);
        GlStateManager.disableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(0.0D, 0.4D, 0.0D);
        GlStateManager.rotate(-ticks * 2.5F, 0, 1, 0);
        RenderUtil.renderItemStack(te.getStackInSlot(0), 0.5F);
        GlStateManager.popMatrix();
    }
}
