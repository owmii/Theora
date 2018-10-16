package xieao.theora.api;

import net.minecraft.entity.player.EntityPlayer;
import xieao.theora.api.player.data.CapabilityPlayerData;
import xieao.theora.api.player.data.PlayerData;

import javax.annotation.Nullable;

public class TheoraAPI {

    @Nullable
    public static PlayerData getPlayerData(EntityPlayer player) {
        return (PlayerData) player.getCapability(CapabilityPlayerData.PLAYER_DATA, null);
    }
}
