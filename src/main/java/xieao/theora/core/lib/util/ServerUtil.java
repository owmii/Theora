package xieao.theora.core.lib.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class ServerUtil {
    public static MinecraftServer getServer() {
        //TODO Server static access? BlockSnapshot
        //return FMLCommonHandler.instance().getMinecraftServerInstance();
        return getWorld(0).getServer();
    }

    public static WorldServer getWorld(int dim) {
        return DimensionManager.getWorld(dim);
    }
}
