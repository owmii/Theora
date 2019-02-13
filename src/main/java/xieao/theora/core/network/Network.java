package xieao.theora.core.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import xieao.theora.core.lib.util.Location;
import xieao.theora.core.lib.util.PlayerUtil;
import xieao.theora.core.network.packet.OpenAbilityGui;
import xieao.theora.core.network.packet.ReqAbilityGui;
import xieao.theora.core.network.packet.SyncAbility;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_CLIENT;

public class Network {
    private static final ResourceLocation ID = Location.ROOT.get("main");
    private static final SimpleChannel CHANNEL;
    private static int id;

    public static void registerAll() {
        register(SyncAbility.class, SyncAbility::encode, SyncAbility::decode, SyncAbility::handle);
        register(ReqAbilityGui.class, (msg, pb) -> {}, pb -> new ReqAbilityGui(), ReqAbilityGui::handle);
        register(OpenAbilityGui.class, OpenAbilityGui::encode, OpenAbilityGui::decode, OpenAbilityGui::handle);
    }

    public static <T> void register(Class<T> clazz, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        CHANNEL.registerMessage(id++, clazz, encoder, decoder, messageConsumer);
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
