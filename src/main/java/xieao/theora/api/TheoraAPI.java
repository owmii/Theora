package xieao.theora.api;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.OptionalCapabilityInstance;
import net.minecraftforge.fml.ModList;
import xieao.theora.api.liquid.Liquid;

import javax.annotation.Nullable;

public class TheoraAPI {

    public static final TheoraAPI API = new TheoraAPI();
    public static final String MOD_ID = "theora";
    public static final String EMPTY = MOD_ID + ":empty";

    public boolean isLoaded() {
        return ModList.get().isLoaded(MOD_ID);
    }

    public static OptionalCapabilityInstance<Liquid.Handler> getLiquidHandler(TileEntity tileEntity, @Nullable EnumFacing side) {
        return tileEntity.getCapability(Liquid.Cap.LIQUID_HANDLER, side);
    }

    public static OptionalCapabilityInstance<Liquid.Handler.Item> getLiquidHandlerItem(ItemStack stack) {
        return stack.getCapability(Liquid.Cap.LIQUID_HANDLER_ITEM, null);
    }
}
