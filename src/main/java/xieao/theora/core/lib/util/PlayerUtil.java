package xieao.theora.core.lib.util;

import net.minecraft.entity.player.EntityPlayerMP;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PlayerUtil {
    @Nullable
    public static EntityPlayerMP get(UUID uuid) {
        return ServerUtil.getServer().getPlayerList().getPlayerByUUID(uuid);
    }

    @Nullable
    public static EntityPlayerMP get(String name) {
        return ServerUtil.getServer().getPlayerList().getPlayerByUsername(name);
    }

    public static List<EntityPlayerMP> getAll() {
        return Collections.unmodifiableList(ServerUtil.getServer().getPlayerList().getPlayers());
    }
}
