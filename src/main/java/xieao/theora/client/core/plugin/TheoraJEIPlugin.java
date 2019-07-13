package xieao.theora.client.core.plugin;


import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.util.ResourceLocation;
import xieao.theora.api.Consts;
import xieao.theora.api.liquid.LiquidHandler;
import xieao.theora.core.IItems;

@JeiPlugin
public class TheoraJEIPlugin implements IModPlugin {
    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(IItems.VIAL, (itemStack) -> {
            LiquidHandler.Item handler = new LiquidHandler.Item(itemStack);
            return handler.getSlot("stored").getLiquid().getDisplayName();
        });
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Consts.MOD_ID, "minecraft");
    }
}
