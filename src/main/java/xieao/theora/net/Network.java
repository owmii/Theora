package xieao.theora.net;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import xieao.theora.Theora;
import xieao.theora.net.packet.IPacket;

public class Network {
    private static final SimpleChannel CHANNEL;
    private static int id;

    private static void registerAll() {

    }

    private static <T extends IPacket> void register(T packet) {
        CHANNEL.registerMessage(id++, packet.getClass(), packet::encode, packet::decode, packet::handle);
    }

    public static <PKT> void toServer(PKT pkt) {
        CHANNEL.sendToServer(pkt);
    }

    public static <PKT> void toClient(PKT pkt, EntityPlayerMP player) {
        CHANNEL.sendTo(pkt, player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
    }

    public static Network creat() {
        return new Network();
    }

    static {
        CHANNEL = NetworkRegistry.ChannelBuilder.named(Theora.loc("main"))
                .clientAcceptedVersions(a -> true)
                .serverAcceptedVersions(a -> true)
                .networkProtocolVersion(() -> NetworkHooks.NETVERSION)
                .simpleChannel();
        registerAll();
    }
}
