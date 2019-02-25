package xieao.theora.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.player.PlayerData;
import xieao.theora.api.recipe.IRecipeRegistry;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public final class TheoraAPI {
    public static final TheoraAPI API = new TheoraAPI();
    private final Set<IRecipeRegistry> recipeRegistries = new HashSet<>();

    public static boolean isLoaded() {
        return ModList.get().isLoaded(Consts.MOD_ID);
    }

    public void register(IRecipeRegistry registry) {
        registry.initRecipes();
        this.recipeRegistries.add(registry);
    }

    public Set<IRecipeRegistry> getRecipeRegistries() {
        return recipeRegistries;
    }

    public static LazyOptional<PlayerData> getPlayerData(EntityPlayer player) {
        return player.getCapability(PlayerData.Capability.DATA);
    }

    public static LazyOptional<Liquid.Handler> getLiquidHandler(TileEntity tileEntity, @Nullable EnumFacing side) {
        return tileEntity.getCapability(Liquid.Capability.HANDLER, side);
    }

    public static LazyOptional<Liquid.Handler.Item> getLiquidHandlerItem(ItemStack stack) {
        return stack.getCapability(Liquid.Capability.HANDLER_ITEM, null);
    }
}
