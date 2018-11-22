package xieao.theora.client.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

/**
 * Death Chamber - xeaio
 * Created using Tabula 7.0.0
 */
public class ModelDeathChamber extends ModelBase {

    public ModelRenderer chaber;
    public ModelRenderer leg0;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;

    public ModelDeathChamber() {
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.leg1 = new ModelRenderer(this, 0, 121);
        this.leg1.setRotationPoint(32.0F, 0.0F, -32.0F);
        this.leg1.addBox(-8.0F, -24.0F, -8.0F, 16, 48, 16, 0.0F);
        this.leg0 = new ModelRenderer(this, 0, 121);
        this.leg0.setRotationPoint(-32.0F, 0.0F, -32.0F);
        this.leg0.addBox(-8.0F, -24.0F, -8.0F, 16, 48, 16, 0.0F);
        this.chaber = new ModelRenderer(this, 0, 0);
        this.chaber.setRotationPoint(0.0F, -40.0F, 0.0F);
        this.chaber.addBox(-24.0F, -32.0F, -24.0F, 48, 64, 48, 0.0F);
        this.leg2 = new ModelRenderer(this, 0, 121);
        this.leg2.setRotationPoint(32.0F, 0.0F, 32.0F);
        this.leg2.addBox(-8.0F, -24.0F, -8.0F, 16, 48, 16, 0.0F);
        this.leg3 = new ModelRenderer(this, 0, 121);
        this.leg3.setRotationPoint(-32.0F, 0.0F, 32.0F);
        this.leg3.addBox(-8.0F, -24.0F, -8.0F, 16, 48, 16, 0.0F);
    }

    public void render(float f5) {
        GlStateManager.pushMatrix();
        this.chaber.render(f5);
        this.leg1.render(f5);
        this.leg0.render(f5);
        this.leg2.render(f5);
        this.leg3.render(f5);
        GlStateManager.popMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
