package xieao.theora;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xieao.theora.api.player.PlayerData;
import xieao.theora.core.handler.Initializer;
import xieao.theora.core.lib.util.ServerUtil;
import xieao.theora.network.NetworkHandler;

@Mod("theora")
public class Theora {
    public static final NetworkHandler NET = new NetworkHandler();

    public Theora() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::common);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueue);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::process);
        MinecraftForge.EVENT_BUS.addListener(ServerUtil::serverStarted);
    }

    void common(FMLCommonSetupEvent event) {
        PlayerData.Cap.register();
        Initializer.load();
        NET.registerAll();
    }

    void client(FMLClientSetupEvent event) {
    }

    void enqueue(InterModEnqueueEvent event) {

    }

    void process(InterModProcessEvent event) {

    }
}
