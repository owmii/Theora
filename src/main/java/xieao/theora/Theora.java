package xieao.theora;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.client.renderer.item.IItemColorHolder;
import xieao.theora.client.renderer.item.TEItemRenderer;
import xieao.theora.client.renderer.tile.TERRegistry;
import xieao.theora.core.IItems;
import xieao.theora.core.command.TheoraCommand;
import xieao.theora.core.config.Config;
import xieao.theora.core.handler.InitHandler;
import xieao.theora.core.network.Network;
import xieao.theora.core.recipe.CauldronRecipes;
import xieao.theora.core.recipe.InteractRecipes;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;
import static xieao.theora.api.TheoraAPI.API;

@Mod(Theora.ID)
public class Theora {
    public static final String ID = "theora";

    public static final Logger LOG = LogManager.getLogger();
    public static final Network NET = Network.creat();

    public Theora() {
        IEventBus eventBus = FMLModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::processIMC);
        EVENT_BUS.addListener(this::starting);
        Config.load();
    }

    void setup(FMLCommonSetupEvent event) {
        Liquid.Cap.register();
        InitHandler.pre(event);

        API.register(new InteractRecipes());
        API.register(new CauldronRecipes());
    }

    void enqueueIMC(InterModEnqueueEvent event) {
        InitHandler.init(event);

        runWhenOnClient(() -> {
            Minecraft mc = Minecraft.getInstance();
            IItems.ITEMS.forEach(item -> {
                TEItemRenderer.register(item);
                if (item instanceof IItemColorHolder) {
                    IItemColorHolder holder = (IItemColorHolder) item;
                    mc.getItemColors().register((holder.getItemColor()), item);
                }
            });
            TERRegistry.registerAll();
        });
    }

    void processIMC(InterModProcessEvent event) {
        InitHandler.post(event);
    }

    void runWhenOnClient(Runnable toRun) {
        if (Dist.CLIENT == FMLEnvironment.dist) {
            toRun.run();
        }
    }

    void starting(FMLServerStartingEvent event) {
        new TheoraCommand(event.getCommandDispatcher());
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(ID, path);
    }
}
