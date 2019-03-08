package xieao.theora.lib.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;
import java.util.UUID;

public class PlayerUtil {
    public static boolean isFake(EntityPlayer player) {
        return player instanceof FakePlayer;
    }

    @Nullable
    public static EntityPlayerMP get(World world, UUID uuid) {
        MinecraftServer server = world.getServer();
        return server.getPlayerList().getPlayerByUUID(uuid);
    }

    @Nullable
    public static EntityPlayerMP get(World world, String name) {
        MinecraftServer server = world.getServer();
        return server.getPlayerList().getPlayerByUsername(name);
    }
}
