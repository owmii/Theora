package xieao.theora.core.lib.util;

import net.minecraft.server.MinecraftServer;

public class ServerUtil {
    @SuppressWarnings("ConstantConditions")
    public static MinecraftServer getServer() {
        //TODO Server static access? BlockSnapshot
        //return FMLCommonHandler.instance().getMinecraftServerInstance();
        return null;
    }
}
