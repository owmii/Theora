package xieao.theora.client.renderer.tesr;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import xieao.theora.Theora;
import xieao.theora.client.helper.ColorHelper;
import xieao.theora.client.renderer.model.ModelDeathChamber;
import xieao.theora.common.block.deathchamber.TileDeathChamber;

public class RenderDeathChamber extends TESRBase<TileDeathChamber> {

    protected static final ModelDeathChamber MODEL_DEATH_CHAMBER = new ModelDeathChamber();

    protected static final ResourceLocation CUBE_TEXTURE = Theora.location("textures/tesr/death_chamber_cube.png");
    protected static final ResourceLocation MAIN_TEXTURE = Theora.location("textures/tesr/death_chamber.png");

    @Override
    public void render(TileDeathChamber te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5D, -1.5D, -0.5D);
        ColorHelper.glColorNormal();
        if (te.buildStatus) {
            GlStateManager.translate(0.0D, 2.0D, -0.0D);
            mc.getTextureManager().bindTexture(MAIN_TEXTURE);
            MODEL_DEATH_CHAMBER.render(0.0625f);
        } else {
            mc.getTextureManager().bindTexture(CUBE_TEXTURE);
            MODEL_CUBE.render(0.0625f);
        }
        GlStateManager.popMatrix();
    }
}
