package xieao.theora.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;
import xieao.theora.api.player.PlayerData;

public final class TheoraAPI {
    public static final TheoraAPI API = new TheoraAPI();

    public static boolean isLoaded() {
        return ModList.get().isLoaded(Consts.MOD_ID);
    }

    public static LazyOptional<PlayerData> getPlayerData(EntityPlayer player) {
        return player.getCapability(PlayerData.Cap.DATA);
    }
}
