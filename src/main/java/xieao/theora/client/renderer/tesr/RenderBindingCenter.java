package xieao.theora.client.renderer.tesr;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.lib.render.tesr.TESRBase;
import xieao.lib.util.ColorUtil;
import xieao.lib.util.RenderUtil;
import xieao.theora.Theora;
import xieao.theora.api.player.ability.Ability;
import xieao.theora.api.recipe.binding.IBindingRecipe;
import xieao.theora.common.block.binding.TileBindingCenter;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class RenderBindingCenter extends TESRBase<TileBindingCenter> {

    public static final ResourceLocation RUNS_TEXTURE = Theora.location("textures/misc/rune_circle_1.png");
    public static final ResourceLocation AURA_TEXTURE = Theora.location("textures/tesr/ability_aura.png");

    @Override
    public void render(TileBindingCenter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        float ticks = RenderUtil.tickCount + partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0.5D, 0.0002D, 0.5D);
        float f0 = ((float) te.maxBuildTicks / ((float) (te.buildTicks + 1) * 10)) / 6.5F;

        ColorUtil.glColor(0xffffff, f0 / 2);
        GlStateManager.pushMatrix();
        GlStateManager.rotate(-ticks / 4.0F, 0, 1, 0);
        RenderUtil.renderQuad(RUNS_TEXTURE, 2.2F);
        GlStateManager.popMatrix();

        ColorUtil.glColor(0x555555, f0 / 2.0F);
        RenderUtil.renderQuad(AURA_TEXTURE, 2.5F);
        GlStateManager.translate(0.0D, 0.0001D, 0.0D);

        ColorUtil.glColor(new Color(0x5D6955).getRGB(), 0.7F);
        IBindingRecipe recipe = te.getCurrentRecipe();
        if (recipe != null) {
            Ability ability = recipe.getResultAbility();
            if (!ability.isEmpty()) {
                GlStateManager.translate(0.0D, 0.01D, 0.0D);
                double d0 = mc.player.posX - (double) ((float) te.getPos().getX() + 0.5F);
                double d1 = mc.player.posZ - (double) ((float) te.getPos().getZ() + 0.5F);
                double d2 = MathHelper.atan2(d1, d0);
                double d3 = Math.toDegrees(-d2);
                GlStateManager.rotate((float) (d3 + 90.0F), 0.0F, 1.0F, 0.0F);
                RenderUtil.renderQuad(ability.getIcon(), 0.6F);
            }
        }

        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}