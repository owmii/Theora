package xieao.theora.network.packet.gui;

import net.minecraftforge.fml.network.NetworkEvent;
import xieao.theora.api.TheoraAPI;

import java.util.function.Supplier;

public class SyncPlayerGuiStatus {
    public static void handle(SyncPlayerGuiStatus msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> TheoraAPI.getPlayerData(ctx.get().getSender()).ifPresent(playerData ->
                playerData.hor.guiOpen = false));
        ctx.get().setPacketHandled(true);
    }
}
