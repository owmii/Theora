package xieao.theora;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import xieao.lib.client.renderer.item.IItemColorHolder;
import xieao.theora.client.renderer.entity.HorRenderer;
import xieao.theora.core.handler.CapabilityHandler;
import xieao.theora.entity.HorEntity;
import xieao.theora.item.IItems;
import xieao.theora.network.Packets;
import xieao.theora.world.gen.IFeatures;

import static xieao.lib.Lollipop.addModListener;

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