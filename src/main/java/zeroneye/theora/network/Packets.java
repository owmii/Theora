package zeroneye.theora.network;

import zeroneye.lib.Lollipop;
import zeroneye.theora.network.packet.SyncPlayerData;

public class Packets {
    public static void register() {
        Lollipop.NET.register(SyncPlayerData.class, SyncPlayerData::encode, SyncPlayerData::decode, SyncPlayerData::handle);
    }
}
