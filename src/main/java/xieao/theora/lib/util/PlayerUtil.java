package xieao.theora.lib.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;
import java.util.UUID;

public class PlayerUtil {
    public static boolean isFake(EntityPlayer player) {
        return player instanceof FakePlayer;
    }

    @Nullable
    public static EntityPlayerMP get(UUID uuid) {
        return ServerUtil.getServer().getPlayerList().getPlayerByUUID(uuid);
    }

    @Nullable
    public static EntityPlayerMP get(String name) {
        return ServerUtil.getServer().getPlayerList().getPlayerByUsername(name);
    }
}
