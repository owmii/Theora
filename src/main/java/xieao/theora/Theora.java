package xieao.theora;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xieao.theora.client.renderer.item.IItemColorHolder;
import xieao.theora.core.IFeatures;
import xieao.theora.core.IItems;
import xieao.theora.core.handler.CapabilityHandler;
import xieao.theora.network.NetworkHandler;

@Mod("theora")
public class Theora {
    public static final NetworkHandler NET = new NetworkHandler();

    public Theora() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::common);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueue);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::process);
    }

    void common(FMLCommonSetupEvent event) {
        CapabilityHandler.register();
        NET.register();
    }

    void enqueue(InterModEnqueueEvent event) {
        IFeatures.register();
    }

    void process(InterModProcessEvent event) {
    }

    void client(FMLClientSetupEvent event) {
        IItems.ITEMS.forEach(item -> {
            if (item instanceof IItemColorHolder)
                Minecraft.getInstance().getItemColors()
                        .register(((IItemColorHolder) item)
                                .getItemColor(), item);
        });
    }
}
