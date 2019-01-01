package xieao.theora.network;

import xieao.lib.network.NetworkWrapper;
import xieao.theora.Theora;
import xieao.theora.network.packets.*;

public class TheoraNetwork {

    public static final NetworkWrapper NET = new NetworkWrapper(Theora.MOD_ID);

    public static void register() {
        int id = 0;
        NET.registerPacket(new PacketSyncAbilities(), id++);
        NET.registerPacket(new PacketRequestAbilitiesGui(), id++);
        NET.registerPacket(new PacketOpenAbilitiesGui(), id++);
        NET.registerPacket(new PacketAbilityStatus(), id++);
        NET.registerPacket(new PacketOpenPigZombieTradeGui(), id++);
        NET.registerPacket(new PacketPigZombieTradBuy(), id++);
        NET.registerPacket(new PacketSyncFlight(), id++);
    }
}
