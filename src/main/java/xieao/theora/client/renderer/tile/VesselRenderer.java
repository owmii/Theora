package xieao.theora.client.renderer.tile;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xieao.theora.api.Consts;
import xieao.theora.block.vessel.TileVessel;
import xieao.theora.client.renderer.model.ModelVessel;

@OnlyIn(Dist.CLIENT)
public class VesselRenderer extends TERenderer<TileVessel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Consts.MOD_ID, "textures/ter/vessel.png");
    private static final ModelVessel MODEL = new ModelVessel();

    @Override
    public void render(TileVessel vessel, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translated(x, y, z);
        GlStateManager.scalef(1.0F, -1.0F, -1.0F);
        GlStateManager.translated(0.5D, -1.5D, -0.5D);
        bindTexture(TEXTURE);
        MODEL.render();
        GlStateManager.popMatrix();
    }
}
