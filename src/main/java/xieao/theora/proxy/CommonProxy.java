package xieao.theora.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import xieao.theora.Theora;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.liquid.LiquidContainerCapability;
import xieao.theora.api.player.data.PlayerDataCapability;
import xieao.theora.common.recipe.FermentingRecipes;
import xieao.theora.common.recipe.RecipeHandler;
import xieao.theora.network.GuiHandler;
import xieao.theora.network.TheoraNetwork;

public class CommonProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        TheoraNetwork.registerPackets();
        PlayerDataCapability.register();
        LiquidContainerCapability.register();

        TheoraAPI.INSTANCE.register(new FermentingRecipes());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Theora.instance, GuiHandler.INSTANCE);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        RecipeHandler.reloadRecipes();
    }
}
