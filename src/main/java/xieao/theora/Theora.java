package xieao.theora;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xieao.theora.api.Consts;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.player.PlayerData;
import xieao.theora.client.core.ITextures;
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

@Mod(Consts.MOD_ID)
public class Theora {
    public Theora() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener((FMLCommonSetupEvent event) -> {
            PlayerData.Cap.register();
            Liquid.Cap.register();
            AutoLoadHandler.load();
            Network.registerAll();
            API.register(new InteractRecipes());
            API.register(new CauldronRecipes());

            // Client
            runWhenOn(Dist.CLIENT, () -> () ->
                    EVENT_BUS.register(ITextures.class));
        });
        eventBus.addListener((InterModEnqueueEvent event) -> {
            // Client
            runWhenOn(Dist.CLIENT, () -> () -> {
                final Minecraft mc = Minecraft.getInstance();
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
    }
}
