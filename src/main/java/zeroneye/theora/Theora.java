package zeroneye.theora;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import zeroneye.lib.client.renderer.item.IItemColorHolder;
import zeroneye.theora.client.renderer.entity.HorRenderer;
import zeroneye.theora.core.handler.CapabilityHandler;
import zeroneye.theora.entity.HorEntity;
import zeroneye.theora.item.IItems;
import zeroneye.theora.network.Packets;
import zeroneye.theora.world.gen.IFeatures;

import static zeroneye.lib.Lollipop.addModListener;

@Mod(Theora.MOD_ID)
public class Theora {
    public static final String MOD_ID = "theora";

    public Theora() {
        addModListener(this::commonSetup);
        addModListener(this::clientSetup);
    }

    void commonSetup(FMLCommonSetupEvent event) {
        CapabilityHandler.register();
        Packets.register();
        IFeatures.register();
    }

    void clientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(HorEntity.class, HorRenderer::new);
        IItemColorHolder.registerAll(IItems.ITEMS);
    }
}