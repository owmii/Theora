package xieao.theora;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLModLoadingContext;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.client.renderer.item.IBlockColorHolder;
import xieao.theora.client.renderer.item.IItemColorHolder;
import xieao.theora.client.renderer.item.TEItemRenderer;
import xieao.theora.client.renderer.tile.TERRegistry;
import xieao.theora.core.command.TheoraCommand;
import xieao.theora.core.handler.AutoLoadHandler;
import xieao.theora.core.handler.RecipeSorter;
import xieao.theora.core.network.Network;
import xieao.theora.core.recipe.CauldronRecipes;
import xieao.theora.core.recipe.InteractRecipes;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;
import static net.minecraftforge.fml.DistExecutor.runWhenOn;
import static xieao.theora.api.TheoraAPI.API;
import static xieao.theora.core.IBlocks.BLOCKS;
import static xieao.theora.core.IItems.ITEMS;

@Mod(Theora.ID)
public class Theora {
    public static final String ID = "theora";

    public Theora() {
        IEventBus eventBus = FMLModLoadingContext.get().getModEventBus();
        eventBus.addListener((FMLCommonSetupEvent event) -> {
            Liquid.Cap.register();
            AutoLoadHandler.load();
            Network.registerAll();
            API.register(new InteractRecipes());
            API.register(new CauldronRecipes());
        });
        //noinspection CodeBlock2Expr TODO remove
        eventBus.addListener((InterModEnqueueEvent event) -> {
            runWhenOn(Dist.CLIENT, () -> () -> {
                Minecraft mc = Minecraft.getInstance();
                ITEMS.forEach(item -> {
                    TEItemRenderer.register(item);
                    if (item instanceof IItemColorHolder) {
                        IItemColorHolder holder = (IItemColorHolder) item;
                        mc.getItemColors().register(holder.getItemColor(), item);
                    }
                });
                BLOCKS.forEach(block -> {
                    if (block instanceof IBlockColorHolder) {
                        IBlockColorHolder holder = (IBlockColorHolder) block;
                        mc.getBlockColors().register(holder.getBlockColor(), block);
                    }
                });
                TERRegistry.registerAll();
            });
        });
        eventBus.addListener((InterModProcessEvent event) -> RecipeSorter.post());
        EVENT_BUS.addListener(TheoraCommand::register);
        //get().registerConfig(ModConfig.Type.SERVER, Config.spec);
    }
}
