package xieao.theora.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import xieao.theora.Theora;
import xieao.theora.network.packets.*;

public class TheoraNetwork {

    public static final SimpleNetworkWrapper NETWORK_WRAPPER = new SimpleNetworkWrapper(Theora.MOD_ID);
    private static int ID;

    public static void registerPackets() {
        registerPacket(new PacketSyncAbilities());
        registerPacket(new PacketRequestAbilitiesGui());
        registerPacket(new PacketOpenAbilitiesGui());
        registerPacket(new PacketAbilityStatus());
        registerPacket(new PacketSyncVial());
        registerPacket(new PacketOpenPigZombieTradeGui());
        registerPacket(new PacketPigZombieTradBuy());
    }

    @SuppressWarnings("unchecked")
    private static void registerPacket(IPacket packet) {
        NETWORK_WRAPPER.registerMessage(packet, packet.getClass(), ID++, packet.getSide());
    }

    public static void sendToAllPlayers(IPacket packet) {
        NETWORK_WRAPPER.sendToAll(packet);
    }

    public static void sendToServer(IPacket packet) {
        NETWORK_WRAPPER.sendToServer(packet);
    }

    public static void sendToPlayer(IPacket packet, EntityPlayerMP player) {
        NETWORK_WRAPPER.sendTo(packet, player);
    }

    public static SimpleNetworkWrapper getNetworkWrapper() {
        return NETWORK_WRAPPER;
    }
}
