package xieao.theora.client.renderer.entity;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xieao.lib.client.model.CubeModel;
import xieao.theora.Theora;
import xieao.theora.entity.HorEntity;

import javax.annotation.Nullable;

public class HorRenderer extends EntityRenderer<HorEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Theora.MOD_ID, "textures/entity/hor.png");
    private final CubeModel model = new CubeModel(3);

    public HorRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(HorEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.translatef((float) x, (float) y + 0.15F, (float) z);
        float f2 = ((float) entity.ticksExisted + partialTicks) * 2.0F;
        GlStateManager.rotatef(-f2 * 0.1F * 180.0F, 0.0F, 1.0F, 0.0F);
        float f3 = 0.03125F;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scalef(-0.7F, -0.7F, 0.7F);
        bindTexture(TEXTURE);
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        int j = 15728880 >> 16 & 65535;
        int k = 15728880 & 65535;
        GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, j, k);
        GlStateManager.enableBlend();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.7F);
        GlStateManager.depthMask(false);
        this.model.render();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.3F);
        GlStateManager.scalef(1.5F, 1.5F, 1.5F);
        this.model.render();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(HorEntity entity) {
        return TEXTURE;
    }
}
