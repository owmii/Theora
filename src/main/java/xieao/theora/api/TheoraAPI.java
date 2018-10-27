package xieao.theora.api;

import net.minecraft.entity.player.EntityPlayer;
import xieao.theora.api.player.data.IPlayerData;
import xieao.theora.api.player.data.PlayerDataCapability;
import xieao.theora.api.recipe.IRecipeRegistry;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class TheoraAPI {

    public static final TheoraAPI INSTANCE = new TheoraAPI();
    private static final Set<IRecipeRegistry> RECIPE_REGISTRIES = new HashSet<>();

    public void register(IRecipeRegistry registry) {
        registry.initRecipes();
        RECIPE_REGISTRIES.add(registry);
    }

    public Set<IRecipeRegistry> getRecipeRegistries() {
        return RECIPE_REGISTRIES;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends IPlayerData> T getPlayerData(EntityPlayer player) {
        return (T) player.getCapability(PlayerDataCapability.PLAYER_DATA, null);
    }
}
