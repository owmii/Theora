package xieao.theora;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.player.PlayerData;
import xieao.theora.block.gate.TileGate;
import xieao.theora.client.gui.inventory.GuiFactory;
import xieao.theora.client.renderer.tile.RenderGate;
import xieao.theora.core.handler.Initializer;
import xieao.theora.lib.util.ServerUtil;
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
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> GuiFactory::get);
    }

    void common(FMLCommonSetupEvent event) {
        PlayerData.Cap.register();
        Liquid.Cap.register();
        Initializer.load();
        NET.registerAll();
    }

    void client(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileGate.class, new RenderGate());
    }

    void enqueue(InterModEnqueueEvent event) {

    }

    void process(InterModProcessEvent event) {

    }
}
