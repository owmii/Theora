package xieao.theora.client.renderer.model;

import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;

public class ModelVessel extends ModelBase {
    public ModelRenderer top;
    public ModelRenderer base;

    public ModelVessel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.top = new ModelRenderer(this, 0, 26);
        this.top.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.top.addBox(-7.0F, 0.0F, -7.0F, 14, 5, 14, 0.0F);
        this.base = new ModelRenderer(this, 0, 0);
        this.base.setRotationPoint(0.0F, 15.0F, 0.0F);
        this.base.addBox(-8.0F, 0.0F, -8.0F, 16, 9, 16, 0.0F);
    }

    public void render() {
        this.top.render(0.0625F);
        this.base.render(0.0625F);
    }
}
