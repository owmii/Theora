package xieao.theora.core.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import xieao.theora.core.lib.util.Location;
import xieao.theora.core.lib.util.PlayerUtil;

import static net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_CLIENT;

public class Network {
    private static final ResourceLocation ID = Location.ROOT.get("main");
    private static final SimpleChannel CHANNEL;
    private int id;

    public static void registerAll() {
    }

    @OnlyIn(Dist.CLIENT)
    public static <T> void toServer(T pkt) {
        CHANNEL.sendToServer(pkt);
    }

    public static <T> void toClient(T pkt, EntityPlayerMP player) {
        CHANNEL.sendTo(pkt, player.connection.getNetworkManager(), PLAY_TO_CLIENT);
    }

    public static <T> void toAll(T pkt) {
        PlayerUtil.getAll().forEach(player ->
                CHANNEL.sendTo(pkt, player.connection.getNetworkManager(), PLAY_TO_CLIENT));
    }

    static {
        CHANNEL = NetworkRegistry.ChannelBuilder.named(ID)
                .clientAcceptedVersions("1"::equals)
                .serverAcceptedVersions("1"::equals)
                .networkProtocolVersion(() -> "1")
                .simpleChannel();
    }
}
