package xieao.theora.core.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import xieao.theora.core.lib.util.Location;

public class Network {
    private static final SimpleChannel CHANNEL;
    private static int id;

    static void registerAll() {

    }

    public static <T> void toServer(T pkt) {
        CHANNEL.sendToServer(pkt);
    }

    public static <T> void toClient(T pkt, EntityPlayerMP player) {
        CHANNEL.sendTo(pkt, player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
    }

    public static Network create() {
        return new Network();
    }

    static {
        CHANNEL = NetworkRegistry.ChannelBuilder.named(Location.ROOT.get("main"))
                .clientAcceptedVersions("1"::equals)
                .serverAcceptedVersions("1"::equals)
                .networkProtocolVersion(() -> "1")
                .simpleChannel();
        registerAll();
    }
}
