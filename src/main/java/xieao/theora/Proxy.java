package xieao.theora;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xieao.lib.proxy.IProxy;
import xieao.lib.renderer.item.IItemColorHolder;
import xieao.lib.renderer.item.TEItemRenderer;
import xieao.lib.util.Registry;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.block.IBlocks;
import xieao.theora.block.ITiles;
import xieao.theora.client.renderer.tile.TERRegistry;
import xieao.theora.core.handler.RecipeSorter;
import xieao.theora.core.recipe.CauldronRecipes;
import xieao.theora.core.recipe.InteractRecipes;
import xieao.theora.entity.IEntities;
import xieao.theora.item.IItems;

import static xieao.theora.api.TheoraAPI.API;

public class Proxy implements IProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        IBlocks.BLOCKS.forEach(Registry::register);
        IItems.ITEMS.forEach(Registry::register);
        ITiles.TYPES.forEach(Registry::register);
        IEntities.TYPES.forEach(Registry::register);
        Liquid.Cap.register();

        API.register(new InteractRecipes());
        API.register(new CauldronRecipes());
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        RecipeSorter.post();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Client extends Proxy {
        @Override
        public void preInit(FMLPreInitializationEvent event) {
            super.preInit(event);
        }

        @Override
        public void init(FMLInitializationEvent event) {
            super.init(event);
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
