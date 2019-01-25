package xieao.theora;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.client.renderer.item.IItemColorHolder;
import xieao.theora.client.renderer.item.TEItemRenderer;
import xieao.theora.client.renderer.tile.TERRegistry;
import xieao.theora.core.IItems;
import xieao.theora.core.command.TheoraCommand;
import xieao.theora.core.config.Config;
import xieao.theora.core.handler.AutoLoadHandler;
import xieao.theora.core.handler.RecipeSorter;
import xieao.theora.core.network.Network;
import xieao.theora.core.recipe.CauldronRecipes;
import xieao.theora.core.recipe.InteractRecipes;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;
import static xieao.theora.api.TheoraAPI.API;

@Mod(Theora.ID)
public class Theora {
    public static final String ID = "theora";
    public static final Network NET = Network.create();

    public Theora() {
        IEventBus eventBus = FMLModLoadingContext.get().getModEventBus();
        eventBus.addListener((FMLCommonSetupEvent event) -> {
            Liquid.Cap.register();
            AutoLoadHandler.load();

            API.register(new InteractRecipes());
            API.register(new CauldronRecipes());
        });
        eventBus.addListener((InterModEnqueueEvent event) -> {
            runWhenOnClient(() -> {
                Minecraft mc = Minecraft.getInstance();
                IItems.ITEMS.forEach(item -> {
                    TEItemRenderer.register(item);
                    if (item instanceof IItemColorHolder) {
                        IItemColorHolder holder = (IItemColorHolder) item;
                        mc.getItemColors().register(holder.getItemColor(), item);
                    }
                });
                TERRegistry.registerAll();
            });
        });
        eventBus.addListener((InterModProcessEvent event) -> RecipeSorter.post());
        EVENT_BUS.addListener(TheoraCommand::register);
        Config.load();
    }

    void runWhenOnClient(Runnable toRun) {
        if (Dist.CLIENT == FMLEnvironment.dist) {
            toRun.run();
        }
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(ID, path);
    }
}
