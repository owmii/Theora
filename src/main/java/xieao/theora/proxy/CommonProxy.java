package xieao.theora.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xieao.lib.proxy.IProxy;
import xieao.lib.util.RegistryUtil;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.liquid.LiquidContainerCapability;
import xieao.theora.api.player.data.PlayerDataCapability;
import xieao.theora.common.ability.TheoraAbilities;
import xieao.theora.common.block.TheoraBlocks;
import xieao.theora.common.book.TheoraBook;
import xieao.theora.common.config.Config;
import xieao.theora.common.entity.TheoraEntities;
import xieao.theora.common.item.TheoraItems;
import xieao.theora.common.item.recipe.BindingRecipes;
import xieao.theora.common.item.recipe.CauldronRecipes;
import xieao.theora.common.item.recipe.LiquidInteractRecipes;
import xieao.theora.common.item.recipe.RecipeHandler;
import xieao.theora.common.item.recipe.crafting.CraftingRecipes;
import xieao.theora.common.liquid.TheoraLiquids;
import xieao.theora.common.trade.PigZomieTrades;
import xieao.theora.common.world.gen.WorldGenShrooms;
import xieao.theora.network.TheoraPackets;

public class CommonProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        Config.preInit(event);
        TheoraPackets.register();
        PlayerDataCapability.register();
        LiquidContainerCapability.register();

        RegistryUtil.registerBlocks(TheoraBlocks.BLOCKS);
        RegistryUtil.registerItems(TheoraItems.ITEMS);

        TheoraLiquids.register();
        TheoraAbilities.register();
        TheoraEntities.register();

        TheoraAPI.API.register(new CauldronRecipes());
        TheoraAPI.API.register(new LiquidInteractRecipes());
        TheoraAPI.API.register(new BindingRecipes());
        CraftingRecipes.initRecipes();
        PigZomieTrades.register();

        TheoraBook.register();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        MinecraftForge.TERRAIN_GEN_BUS.register(WorldGenShrooms.class);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        RecipeHandler.sortRecipes();
        PigZomieTrades.postInit();
    }
}
