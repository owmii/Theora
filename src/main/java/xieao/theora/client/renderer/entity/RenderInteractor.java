package xieao.theora.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import xieao.lib.util.ColorUtil;
import xieao.lib.util.RenderUtil;
import xieao.theora.client.renderer.TheoraTextureMap;
import xieao.theora.common.entity.EntityInteractor;

import javax.annotation.Nullable;
import java.awt.*;

public class RenderInteractor extends Render<EntityInteractor> {

    public RenderInteractor(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityInteractor entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + .5, y + 1.0F, z + .5);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableBlend(); //TODO fix
        // GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        ColorUtil.glColor(new Color(entity.getColor()).darker(), 1.0F);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0001F, 0.0F);
        RenderUtil.renderQuad(TheoraTextureMap.LIQUID_INTERACT_OV, 1.0D);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, -0.5F, 0.5001F);
        GlStateManager.rotate(90.0F, 1, 0, 0);
        RenderUtil.renderQuad(TheoraTextureMap.LIQUID_INTERACT_OV, 1.0D);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.5001F, -0.5F, 0.0F);
        GlStateManager.rotate(90.0F, 0, 0, 1);
        RenderUtil.renderQuad(TheoraTextureMap.LIQUID_INTERACT_OV, 1.0D);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, -0.5F, -0.5001F);
        GlStateManager.rotate(-90.0F, 1, 0, 0);
        RenderUtil.renderQuad(TheoraTextureMap.LIQUID_INTERACT_OV, 1.0D);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5001F, -0.5F, 0.0F);
        GlStateManager.rotate(-90.0F, 0, 0, 1);
        RenderUtil.renderQuad(TheoraTextureMap.LIQUID_INTERACT_OV, 1.0D);
        GlStateManager.popMatrix();

        GlStateManager.disableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
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
