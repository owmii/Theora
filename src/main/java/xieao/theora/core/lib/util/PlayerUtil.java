package xieao.theora.core.lib.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.FakePlayer;

public class PlayerUtil {

    public static boolean isFake(PlayerEntity player) {
        return player instanceof FakePlayer;
    }

//    @Nullable
//    public static ServerPlayerEntity get(UUID uuid) {
//        return ServerUtil.getServer().play().getPlayerByUUID(uuid);
//    }
//
//    @Nullable
//    public static ServerPlayerEntity get(String name) {
//        return ServerUtil.getServer().getPlayerList().getPlayerByUsername(name);
//    }
}
