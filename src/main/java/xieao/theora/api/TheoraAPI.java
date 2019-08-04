package xieao.theora.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;
import xieao.theora.Theora;
import xieao.theora.api.player.PlayerData;

public final class TheoraAPI {
    public static final TheoraAPI API = new TheoraAPI();

    public static boolean isLoaded() {
        return ModList.get().isLoaded(Theora.MOD_ID);
    }

    public static LazyOptional<PlayerData> getPlayerData(PlayerEntity player) {
        return player.getCapability(PlayerData.Cap.DATA);
    }
}
