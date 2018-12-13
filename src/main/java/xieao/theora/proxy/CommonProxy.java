package xieao.theora.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import xieao.theora.Theora;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.liquid.LiquidContainerCapability;
import xieao.theora.api.player.data.PlayerDataCapability;
import xieao.theora.common.ability.TheoraAbilities;
import xieao.theora.common.enchantment.TheoraEnchantments;
import xieao.theora.common.entity.TheoraEntities;
import xieao.theora.common.lib.book.TheoraBook;
import xieao.theora.common.lib.config.Config;
import xieao.theora.common.lib.recipe.BindingRecipes;
import xieao.theora.common.lib.recipe.CauldronRecipes;
import xieao.theora.common.lib.recipe.LiquidInteractRecipes;
import xieao.theora.common.lib.recipe.RecipeHandler;
import xieao.theora.common.lib.recipe.crafting.CraftingRecipes;
import xieao.theora.common.lib.trade.PigZomieTrades;
import xieao.theora.common.liquid.TheoraLiquids;
import xieao.theora.common.world.gen.WorldGenShrooms;
import xieao.theora.network.GuiHandler;
import xieao.theora.network.TheoraNetwork;

import static xieao.theora.common.block.TheoraBlocks.BLOCKS;
import static xieao.theora.common.item.TheoraItems.ITEMS;

public class CommonProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        Config.preInit(event);
        TheoraNetwork.register();
        PlayerDataCapability.register();
        LiquidContainerCapability.register();

        ForgeRegistries.BLOCKS.registerAll(BLOCKS.toArray(new Block[0]));
        ForgeRegistries.ITEMS.registerAll(ITEMS.toArray(new Item[0]));

        TheoraEnchantments.register();
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
        NetworkRegistry.INSTANCE.registerGuiHandler(Theora.instance, GuiHandler.INSTANCE);
        MinecraftForge.TERRAIN_GEN_BUS.register(WorldGenShrooms.class);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        RecipeHandler.sortRecipes();
        PigZomieTrades.postInit();
    }
}
