package xieao.theora.lib.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;

import java.util.UUID;

public class PlayerUtil {
    public static boolean isFake(EntityPlayer player) {
        return player instanceof FakePlayer;
    }

    public static LazyOptional<EntityPlayer> get(World world, UUID uuid) {
        MinecraftServer server = world.getServer();
        EntityPlayer player = server.getPlayerList().getPlayerByUUID(uuid);
        return player != null ? LazyOptional.of(() -> player) : LazyOptional.empty();
    }

    public static LazyOptional<EntityPlayer> get(World world, String name) {
        MinecraftServer server = world.getServer();
        EntityPlayer player = server.getPlayerList().getPlayerByUsername(name);
        return player != null ? LazyOptional.of(() -> player) : LazyOptional.empty();
    }
}
