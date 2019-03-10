package xieao.theora.client.model;

import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;

/**
 * ModelGate - xieao
 * Created using Tabula 7.0.0
 */
public class ModelGate extends ModelBase {
    public ModelRenderer base;
    public ModelRenderer top;

    public ModelGate() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.top = new ModelRenderer(this, 0, 27);
        this.top.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.top.addBox(-7.0F, 0.0F, -7.0F, 14, 21, 14, 0.0F);
        this.base = new ModelRenderer(this, 0, 0);
        this.base.setRotationPoint(0.0F, 13.0F, 0.0F);
        this.base.addBox(-8.0F, 0.0F, -8.0F, 16, 11, 16, 0.0F);
    }

    public void render() {
        this.top.render(0.0625f);
        this.base.render(0.0625f);
    }
}
