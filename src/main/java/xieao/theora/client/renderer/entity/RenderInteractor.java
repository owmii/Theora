package xieao.theora.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import xieao.theora.Theora;
import xieao.theora.client.helper.ColorHelper;
import xieao.theora.client.helper.RendererHelper;
import xieao.theora.common.entity.EntityInteractor;

import javax.annotation.Nullable;

public class RenderInteractor extends Render<EntityInteractor> {

    private static final ResourceLocation OV = Theora.location("textures/misc/liquid_interact_ov.png");

    public RenderInteractor(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityInteractor entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + .5, y + 1.0F, z + .5);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        ColorHelper.glColor(entity.getColor(), 1.0F);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0001F, 0.0F);
        RendererHelper.renderQuad(OV, 1.0D);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, -0.5F, 0.5001F);
        GlStateManager.rotate(90.0F, 1, 0, 0);
        RendererHelper.renderQuad(OV, 1.0D);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.5001F, -0.5F, 0.0F);
        GlStateManager.rotate(90.0F, 0, 0, 1);
        RendererHelper.renderQuad(OV, 1.0D);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, -0.5F, -0.5001F);
        GlStateManager.rotate(-90.0F, 1, 0, 0);
        RendererHelper.renderQuad(OV, 1.0D);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5001F, -0.5F, 0.0F);
        GlStateManager.rotate(-90.0F, 0, 0, 1);
        RendererHelper.renderQuad(OV, 1.0D);
        GlStateManager.popMatrix();

        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    @Override
    public boolean isMultipass() {
        return true;
    }

    @Override
    public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {

    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityInteractor entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
