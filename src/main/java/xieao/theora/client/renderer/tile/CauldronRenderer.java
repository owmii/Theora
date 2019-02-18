package xieao.theora.client.renderer.tile;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xieao.theora.api.Consts;
import xieao.theora.block.cauldron.TileCauldron;
import xieao.theora.client.core.lib.util.Draw3d;

@OnlyIn(Dist.CLIENT)
public class CauldronRenderer extends TERenderer<TileCauldron> {
    public static final ResourceLocation FILL = new ResourceLocation(Consts.MOD_ID, "textures/misc/cauldron_fill.png");

    @Override
    public void render(TileCauldron tile, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translated(x + 0.5D, y, z + 0.5D);
        bindTexture(FILL);
        Tessellator tessellator = Tessellator.getInstance();
        Draw3d.quad(tessellator.getBuffer(), EnumFacing.UP, 1.0D, 1.0D);
        tessellator.draw();
        GlStateManager.popMatrix();
    }
}
