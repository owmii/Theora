package xieao.theora.client.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Cube - xeaio
 * Created using Tabula 7.0.0
 */

public class ModelCube extends ModelBase {

    public ModelRenderer cube;

    public ModelCube() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.cube = new ModelRenderer(this, 0, 0);
        this.cube.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.cube.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
    }

    public void render(float f5) {
        this.cube.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
