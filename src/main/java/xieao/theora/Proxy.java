package xieao.theora;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.client.renderer.item.IItemColorHolder;
import xieao.theora.client.renderer.item.TEItemRenderer;
import xieao.theora.client.renderer.tile.TERRegistry;
import xieao.theora.core.IItems;
import xieao.theora.core.handler.ModInitializer;
import xieao.theora.core.recipe.CauldronRecipes;
import xieao.theora.core.recipe.InteractRecipes;

import static xieao.theora.api.TheoraAPI.API;

public class Proxy {
    public void setup(FMLCommonSetupEvent event) {
        ModInitializer.setup();
        Liquid.Cap.register();

        API.register(new InteractRecipes());
        API.register(new CauldronRecipes());
    }

    public void enqueueIMC(InterModEnqueueEvent event) {
        ModInitializer.enqueueIMC();
    }

    public void processIMC(InterModProcessEvent event) {
        ModInitializer.processIMC();
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
