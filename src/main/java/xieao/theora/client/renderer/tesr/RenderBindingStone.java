package xieao.theora.client.renderer.tesr;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import xieao.theora.api.recipe.bindingstone.IBindingStoneRecipe;
import xieao.theora.client.helper.ColorHelper;
import xieao.theora.client.helper.RendererHelper;
import xieao.theora.common.block.bindingstone.TileBindingStone;

public class RenderBindingStone extends TESRBase<TileBindingStone> {

    @Override
    public void render(TileBindingStone te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0.5D, 1 / 15.9F, 0.5D);
        RenderHelper.disableStandardItemLighting();
        ColorHelper.glColor(0xc2bbcc);
        IBindingStoneRecipe recipe = te.getCurrentRecipe();
        if (recipe != null && !recipe.getResultAbility().isEmpty()) {
            ResourceLocation regName = recipe.getResultAbility().getRegistryName();
            mc.getTextureManager().bindTexture(new ResourceLocation(regName.getResourceDomain(), "textures/abilities/" + regName.getResourcePath() + ".png"));
            RendererHelper.renderFlatQuad(1.0F);
        }
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
}