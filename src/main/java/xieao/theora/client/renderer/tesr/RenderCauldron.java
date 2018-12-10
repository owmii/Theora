package xieao.theora.client.renderer.tesr;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import xieao.theora.Theora;
import xieao.theora.api.liquid.LiquidSlot;
import xieao.theora.client.helper.ColorHelper;
import xieao.theora.client.helper.RendererHelper;
import xieao.theora.common.block.TheoraBlocks;
import xieao.theora.common.block.cauldron.TileCauldron;

public class RenderCauldron extends TESRBase<TileCauldron> {

    public static final ResourceLocation FILL_0 = Theora.location("textures/misc/fill/cauldron_fill_0.png");
    public static final ResourceLocation FILL_1 = Theora.location("textures/misc/fill/cauldron_fill_1.png");

    @Override
    public void render(TileCauldron te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        float ticks = te.tickCount + partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderHelper.disableStandardItemLighting();
        if (te.heated) {
            bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            RendererHelper.render(TheoraBlocks.EMBER.getDefaultState(), mc.world, BlockPos.ORIGIN);
        }
        LiquidSlot liquidTank = te.getLiquidSlot(0);
        boolean hasWater = te.hasWater();
        if (te.fill > 0 || hasWater) {
            float blend = te.blend / (float) te.boilingTime;
            int color0 = hasWater ? ColorHelper.blend(0x008CEE, te.nextLiquidColors[0], blend) : te.nextLiquidColors[0];
            int color1 = hasWater ? ColorHelper.blend(0x7cf8ff, te.nextLiquidColors[1], blend) : te.nextLiquidColors[1];
            float fill = hasWater ? 0.72F : (te.fill * (0.72F - 0.35F)) / liquidTank.getCapacity() + 0.35F;
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.5D, fill, 0.5D);
            ColorHelper.glColor(color1);
            RendererHelper.renderQuad(FILL_0, 0.85D);
            GlStateManager.translate(0.0D, 0.0002D, 0.0D);
            ColorHelper.glColor(color0);
            RendererHelper.renderQuad(FILL_1, 0.855D);
            GlStateManager.popMatrix();
        }
        RenderHelper.enableStandardItemLighting();
        if (!te.started) {
            RendererHelper.renderRotatingItems(te.getStacks(), 0.5F, ticks * 0.8F, 0.7D, 0.3D);
        }
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
