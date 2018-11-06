package xieao.theora.client.renderer.tesr;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import xieao.theora.Theora;
import xieao.theora.api.recipe.bindingstone.IBindingStoneRecipe;
import xieao.theora.client.helper.ColorHelper;
import xieao.theora.client.helper.RendererHelper;
import xieao.theora.common.block.bindingstone.TileBindingStone;

public class RenderBindingStone extends TESRBase<TileBindingStone> {

    @Override
    public void render(TileBindingStone te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        float ticks = RendererHelper.tickCount + partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0.5D, 1 / 15.9F, 0.5D);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.SRC_ALPHA);
        ColorHelper.glColor(0xffffff, 0.4F);
        GlStateManager.pushMatrix();
        GlStateManager.rotate(-ticks, 0, 1, 0);
        RendererHelper.renderQuad(Theora.location("textures/misc/rune_circle_1.png"), 2.5F);
        GlStateManager.popMatrix();
        ColorHelper.glColor(0xffffff, 0.7F);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        //ColorHelper.glColor(0xc2bbcc);
        IBindingStoneRecipe recipe = te.getCurrentRecipe();
        if (recipe != null && !recipe.getResultAbility().isEmpty()) {
            ResourceLocation regName = recipe.getResultAbility().getRegistryName();
            RendererHelper.renderQuad(new ResourceLocation(regName.getResourceDomain(), "textures/abilities/" + regName.getResourcePath() + ".png"), 1.0F);
        }
        GlStateManager.disableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
}