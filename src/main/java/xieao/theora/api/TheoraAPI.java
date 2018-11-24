package xieao.theora.api;

import net.minecraft.entity.player.EntityPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xieao.theora.api.player.data.IPlayerData;
import xieao.theora.api.player.data.PlayerDataCapability;
import xieao.theora.api.recipe.IRecipeRegistry;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class TheoraAPI {

    public static final TheoraAPI API = new TheoraAPI();
    private final Set<IRecipeRegistry> recipeRegistries = new HashSet<>();

    public static final Logger LOG = LogManager.getLogger("theora");

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends IPlayerData> T getPlayerData(EntityPlayer player) {
        return (T) player.getCapability(PlayerDataCapability.PLAYER_DATA, null);
    }

    public void register(IRecipeRegistry registry) {
        registry.initRecipes();
        recipeRegistries.add(registry);
    }

    public Set<IRecipeRegistry> getRecipeRegistries() {
        return recipeRegistries;
    }
}
