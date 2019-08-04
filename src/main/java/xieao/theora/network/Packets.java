package xieao.theora.network;

import xieao.lib.Lollipop;
import xieao.theora.network.packet.SyncPlayerData;

public class Packets {
    public static void register() {
        Lollipop.NET.register(SyncPlayerData.class, SyncPlayerData::encode, SyncPlayerData::decode, SyncPlayerData::handle);
    }
}
