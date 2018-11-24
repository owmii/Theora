package xieao.theora.client.renderer.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import xieao.theora.Theora;
import xieao.theora.common.entity.EntitySoul;

import javax.annotation.Nullable;

public class RenderSoul extends Render<EntitySoul> {

    protected RenderSoul(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntitySoul entity, double x, double y, double z, float entityYaw, float partialTicks) {
//        GlStateManager.pushMatrix();
//        GlStateManager.translate(x, y + .3, z);
//        RenderHelper.disableStandardItemLighting();
//        GlStateManager.enableBlend();
//        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
//        RendererHelper.renderFacingQuad(Theora.location("textures/entity/soul.png"), 0.5F);
//        GlStateManager.disableBlend();
//        RenderHelper.enableStandardItemLighting();
//        GlStateManager.popMatrix();
//        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntitySoul entity) {
        return Theora.location("textures/misc/wand_hilight.png");
    }
}
