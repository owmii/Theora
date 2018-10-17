package xieao.theora.api;

import net.minecraft.entity.player.EntityPlayer;
import xieao.theora.api.player.data.CapabilityPlayerData;
import xieao.theora.api.player.data.IPlayerData;

import javax.annotation.Nullable;

public class TheoraAPI {

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends IPlayerData> T getPlayerData(EntityPlayer player) {
        return (T) player.getCapability(CapabilityPlayerData.PLAYER_DATA, null);
    }
}
