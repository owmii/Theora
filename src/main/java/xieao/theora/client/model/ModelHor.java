package xieao.theora.client.model;

import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;

/**
 * ModelHor - xieao
 * Created using Tabula 7.0.1
 */
public class ModelHor extends ModelBase {
    public ModelRenderer hor;

    public ModelHor() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.hor = new ModelRenderer(this, 0, 0);
        this.hor.setRotationPoint(-8.0F, 24.0F, -8.0F);
        this.hor.addBox(0.0F, 0.0F, 0.0F, 32, 64, 32, 0.0F);
    }

    public void render() {
        this.hor.render(0.0625f);
    }
}
