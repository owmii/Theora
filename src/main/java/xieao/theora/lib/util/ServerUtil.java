package xieao.theora.lib.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

import javax.annotation.Nullable;

public class ServerUtil {
    private static MinecraftServer server;

    @Nullable
    public static WorldServer getWorld(int dimId) {
        return getWorld(DimensionType.getById(dimId));
    }

    @Nullable
    public static WorldServer getWorld(ResourceLocation dimName) {
        return getWorld(DimensionType.byName(dimName));
    }

    @Nullable
    public static WorldServer getWorld(DimensionType dim) {
        return DimensionManager.getWorld(getServer(), dim, false, false);
    }

    public static MinecraftServer getServer() {
        return server;
    }

    public static void serverStarted(FMLServerStartedEvent event) {
        ServerUtil.server = event.getServer();
    }
}
