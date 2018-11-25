package xieao.theora.client.renderer.tesr;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import xieao.theora.Theora;
import xieao.theora.client.helper.ColorHelper;
import xieao.theora.client.helper.RendererHelper;
import xieao.theora.client.renderer.model.ModelDeathChamber;
import xieao.theora.common.block.deathchamber.TileDeathChamber;

public class RenderDeathChamber extends TESRBase<TileDeathChamber> {

    protected static final ModelDeathChamber MODEL_DEATH_CHAMBER = new ModelDeathChamber();

    protected static final ResourceLocation CUBE_TEXTURE = Theora.location("textures/tesr/death_chamber_cube.png");
    protected static final ResourceLocation MAIN_TEXTURE = Theora.location("textures/tesr/death_chamber.png");

    @Override
    public void render(TileDeathChamber te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.translate(x, y, z);
        ColorHelper.glColorNormal();
        if (te.built) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F, -1.0F, -1.0F);
            GlStateManager.translate(0.5D, 0.5D, -0.5D);
            this.mc.getTextureManager().bindTexture(MAIN_TEXTURE);
            MODEL_DEATH_CHAMBER.render(0.0625f);
            GlStateManager.popMatrix();
            if (!te.getStackInSlot(0).isEmpty()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.5D, -0.01D, 0.5D);
                GlStateManager.rotate(90, 1, 0, 0);
                RendererHelper.renderItemStack(te.getStackInSlot(0), 0.7F);
                GlStateManager.popMatrix();
            }
            if (!te.getStackInSlot(1).isEmpty()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.5D, 1.5D, -1.01D);
                GlStateManager.rotate(0, 0, 1, 0);
                RendererHelper.renderItemStack(te.getStackInSlot(1), 0.85F);
                GlStateManager.popMatrix();
            }
            if (!te.getStackInSlot(2).isEmpty()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.5D, 1.5D, 2.01D);
                GlStateManager.rotate(180, 0, 1, 0);
                RendererHelper.renderItemStack(te.getStackInSlot(2), 0.85F);
                GlStateManager.popMatrix();
            }
            if (!te.getStackInSlot(3).isEmpty()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(-1.01D, 1.5D, 0.5D);
                GlStateManager.rotate(90, 0, 1, 0);
                RendererHelper.renderItemStack(te.getStackInSlot(3), 0.85F);
                GlStateManager.popMatrix();
            }
            if (!te.getStackInSlot(4).isEmpty()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(2.01D, 1.5D, 0.5D);
                GlStateManager.rotate(270, 0, 1, 0);
                RendererHelper.renderItemStack(te.getStackInSlot(4), 0.85F);
                GlStateManager.popMatrix();
            }
        } else {
            GlStateManager.scale(1.0F, -1.0F, -1.0F);
            GlStateManager.translate(0.5D, -1.5D, -0.5D);
            this.mc.getTextureManager().bindTexture(CUBE_TEXTURE);
            MODEL_CUBE.render(0.0625f);
        }
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
