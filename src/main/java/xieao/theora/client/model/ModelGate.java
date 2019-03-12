package xieao.theora.client.model;

import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;

/**
 * ModelGate - xieao
 * Created using Tabula 7.0.1
 */
public class ModelGate extends ModelBase {
    public ModelRenderer gate;

    public ModelGate() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.gate = new ModelRenderer(this, 0, 0);
        this.gate.setRotationPoint(-8.0F, 24.0F, -8.0F);
        this.gate.addBox(0.0F, 0.0F, 0.0F, 32, 64, 32, 0.0F);
    }

    public void render() {
        this.gate.render(0.0625f);
    }
}
