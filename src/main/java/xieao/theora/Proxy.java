package xieao.theora;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.block.IBlocks;
import xieao.theora.block.ITiles;
import xieao.theora.client.renderer.item.IItemColorHolder;
import xieao.theora.client.renderer.item.TEItemRenderer;
import xieao.theora.client.renderer.tile.TERRegistry;
import xieao.theora.core.ISounds;
import xieao.theora.core.handler.RecipeSorter;
import xieao.theora.core.lib.util.Registry;
import xieao.theora.core.recipe.CauldronRecipes;
import xieao.theora.core.recipe.InteractRecipes;
import xieao.theora.entity.IEntities;
import xieao.theora.item.IItems;

import static xieao.theora.api.TheoraAPI.API;

public class Proxy {
    public void setup(FMLCommonSetupEvent event) {
        IBlocks.BLOCKS.forEach(Registry::register);
        IItems.ITEMS.forEach(Registry::register);
        ITiles.TYPES.forEach(Registry::register);
        IEntities.TYPES.forEach(Registry::register);
        ISounds.SOUNDS.forEach(Registry::register);
        Liquid.Cap.register();

        API.register(new InteractRecipes());
        API.register(new CauldronRecipes());
    }

    public void enqueueIMC(InterModEnqueueEvent event) {

    }

    public void processIMC(InterModProcessEvent event) {
        RecipeSorter.post();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Client extends Proxy {
        @Override
        public void setup(FMLCommonSetupEvent event) {
            super.setup(event);
        }

        @Override
        public void enqueueIMC(InterModEnqueueEvent event) {
            super.enqueueIMC(event);
            TERRegistry.registerAll();
            IItems.ITEMS.forEach(item -> {
                TEItemRenderer.register(item);
                if (item instanceof IItemColorHolder) {
                    Minecraft.getInstance().getItemColors()
                            .register(((IItemColorHolder) item).getItemColor(), item);
                }
            });
        }
    }
}
