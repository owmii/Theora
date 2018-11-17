package xieao.theora.client.renderer.tesr;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.Theora;
import xieao.theora.api.recipe.binding.IBindingRecipe;
import xieao.theora.client.helper.ColorHelper;
import xieao.theora.client.helper.RendererHelper;
import xieao.theora.common.block.binding.TileBindingCenter;

@SideOnly(Side.CLIENT)
public class RenderBindingCenter extends TESRBase<TileBindingCenter> {

    public static final ResourceLocation RUNS_TEXTURE = Theora.location("textures/misc/rune_circle_1.png");
    public static final ResourceLocation AURA_TEXTURE = Theora.location("textures/tesr/ability_aura.png");

    @Override
    public void render(TileBindingCenter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        float ticks = RendererHelper.tickCount + partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0.5D, 0.0002D, 0.5D);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        float f0 = ((float) te.maxBuildTicks / ((float) (te.buildTicks + 1) * 10)) / 6.5F;
        ColorHelper.glColor(0x999999, f0);
        GlStateManager.pushMatrix();
        GlStateManager.rotate(-ticks, 0, 1, 0);
        RendererHelper.renderQuad(RUNS_TEXTURE, 2.5F);
        GlStateManager.popMatrix();
        ColorHelper.glColor(0x555555, f0 / 2.0F);
        RendererHelper.renderQuad(AURA_TEXTURE, 2.5F);
        GlStateManager.translate(0.0D, 0.0001D, 0.0D);
        ColorHelper.glColor(0xffffff, 0.7F);
        IBindingRecipe recipe = te.getCurrentRecipe();
        if (recipe != null && !recipe.getResultAbility().isEmpty()) {
            ResourceLocation regName = recipe.getResultAbility().getRegistryName();
            GlStateManager.translate(0.0D, 0.5D, 0.0D);
            RendererHelper.renderFacingQuad(new ResourceLocation(regName.getResourceDomain(), "textures/abilities/" + regName.getResourcePath() + ".png"), 0.7F);
        }
        GlStateManager.disableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
}