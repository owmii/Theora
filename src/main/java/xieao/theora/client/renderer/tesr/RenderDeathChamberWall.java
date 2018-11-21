package xieao.theora.client.renderer.tesr;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import xieao.theora.Theora;
import xieao.theora.client.helper.ColorHelper;
import xieao.theora.common.block.deathchamber.TileDeathChamberWall;

public class RenderDeathChamberWall extends TESRBase<TileDeathChamberWall> {

    protected static final ResourceLocation CUBE_TEXTURE = Theora.location("textures/tesr/death_chamber_wall.png");

    @Override
    public void render(TileDeathChamberWall te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5D, -1.5D, -0.5D);
        ColorHelper.glColorNormal();
        if (!te.hasDeathChamber()) {
            mc.getTextureManager().bindTexture(CUBE_TEXTURE);
            MODEL_CUBE.render(0.0625f);
        }
        GlStateManager.popMatrix();
    }
}
