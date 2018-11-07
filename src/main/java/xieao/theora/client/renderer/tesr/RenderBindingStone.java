package xieao.theora.client.renderer.tesr;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.Theora;
import xieao.theora.api.recipe.bindingstone.IBindingStoneRecipe;
import xieao.theora.client.helper.ColorHelper;
import xieao.theora.client.helper.RendererHelper;
import xieao.theora.common.block.bindingstone.TileBindingStone;

@SideOnly(Side.CLIENT)
public class RenderBindingStone extends TESRBase<TileBindingStone> {

    public static final ResourceLocation RUNS_TEXTURE = Theora.location("textures/misc/rune_circle_1.png");
    public static final ResourceLocation AURA_TEXTURE = Theora.location("textures/tesr/ability_aura.png");

    @Override
    public void render(TileBindingStone te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        float ticks = RendererHelper.tickCount + partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0.5D, 0.0002D, 0.5D);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableBlend();
        //GlStateManager.blendFunc(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.SRC_ALPHA);
        //GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        ColorHelper.glColor(0x999999, 0.3F);
        GlStateManager.pushMatrix();
        GlStateManager.rotate(-ticks, 0, 1, 0);
        RendererHelper.renderQuad(RUNS_TEXTURE, 2.5F);
        GlStateManager.popMatrix();
        ColorHelper.glColor(0xffffff, 0.4F);
        RendererHelper.renderQuad(AURA_TEXTURE, 2.5F);
        GlStateManager.translate(0.0D, 0.0001D, 0.0D);
        ColorHelper.glColor(0xffffff, 0.3F);
        IBindingStoneRecipe recipe = te.getCurrentRecipe();
        if (recipe != null && !recipe.getResultAbility().isEmpty()) {
            ResourceLocation regName = recipe.getResultAbility().getRegistryName();
            GlStateManager.scale(.7, 0, .7);
            RendererHelper.renderQuad(new ResourceLocation(regName.getResourceDomain(), "textures/abilities/" + regName.getResourcePath() + ".png"), 1.0F);
        }
        GlStateManager.disableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
}