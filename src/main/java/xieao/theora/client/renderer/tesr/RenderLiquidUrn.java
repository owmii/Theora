package xieao.theora.client.renderer.tesr;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import xieao.lib.util.ColorUtil;
import xieao.lib.util.RenderUtil;
import xieao.theora.Theora;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.common.block.liquidurn.TileLiquidUrn;

public class RenderLiquidUrn extends TESRBase<TileLiquidUrn> {

    public static final ResourceLocation OV = Theora.location("textures/misc/liquid_urn_ov.png");

    @Override
    public void render(TileLiquidUrn te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        EnumFacing facing = te.getFacing();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        switch (facing) {
            case NORTH:
                break;
            case SOUTH:
                GlStateManager.translate(1, 0, 1);
                GlStateManager.rotate(180, 0, 1, 0);
                break;
            case WEST:
                GlStateManager.translate(0, 0, 1);
                GlStateManager.rotate(90, 0, 1, 0);
                break;
            case EAST:
                GlStateManager.translate(1, 0, 0);
                GlStateManager.rotate(270, 0, 1, 0);
                break;
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate(.5, 0.3125f, 0.1243f);
        GlStateManager.rotate(-90, 1, 0, 0);
        Liquid liquid = te.getLiquidSlot().getLiquid();
        ColorUtil.glColor(liquid.isEmpty() ? 0xf0f0f0 : liquid.getDarkColor());
        RenderUtil.renderQuad(OV, 0.5f);
        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
