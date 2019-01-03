package xieao.theora.network;

import xieao.theora.Theora;
import xieao.theora.network.packets.*;

public class TheoraPackets {

    public static void register() {
        Theora.NET.register(new PacketSyncAbilities());
        Theora.NET.register(new PacketRequestAbilitiesGui());
        Theora.NET.register(new PacketOpenAbilitiesGui());
        Theora.NET.register(new PacketAbilityStatus());
        Theora.NET.register(new PacketOpenPigZombieTradeGui());
        Theora.NET.register(new PacketPigZombieTradBuy());
        Theora.NET.register(new PacketSyncFlight());
    }
}
