package xieao.theora.client.renderer.entity;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import xieao.theora.common.entity.EntityInteractor;

public class EntityRenderer {

    public static void register() {
        RenderingRegistry.registerEntityRenderingHandler(EntityInteractor.class, RenderInteractor::new);
    }

}
