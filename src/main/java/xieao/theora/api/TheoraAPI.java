package xieao.theora.api;

import net.minecraft.entity.player.EntityPlayer;
import xieao.theora.api.player.data.IPlayerData;
import xieao.theora.api.player.data.PlayerDataCapability;

import javax.annotation.Nullable;

public class TheoraAPI {

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends IPlayerData> T getPlayerData(EntityPlayer player) {
        return (T) player.getCapability(PlayerDataCapability.PLAYER_DATA, null);
    }
}
