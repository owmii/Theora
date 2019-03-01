package xieao.theora.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import xieao.theora.api.Consts;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_CLIENT;

public class NetworkHandler {
    private static final ResourceLocation ID = new ResourceLocation(Consts.MOD_ID, "main");
    private static final SimpleChannel CHANNEL;
    private static int id;

    public void registerAll() {

    }

    public <T> void register(Class<T> clazz, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        CHANNEL.registerMessage(id++, clazz, encoder, decoder, messageConsumer);
    }

    @OnlyIn(Dist.CLIENT)
    public <T> void toServer(T msg) {
        CHANNEL.sendToServer(msg);
    }

    public <T> void toClient(T msg, EntityPlayerMP player) {
        CHANNEL.sendTo(msg, player.connection.getNetworkManager(), PLAY_TO_CLIENT);
    }

    static {
        CHANNEL = NetworkRegistry.ChannelBuilder.named(ID)
                .clientAcceptedVersions("1"::equals)
                .serverAcceptedVersions("1"::equals)
                .networkProtocolVersion(() -> "1")
                .simpleChannel();
    }
}
