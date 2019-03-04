package xieao.theora.network.packet.playerdata;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.PlayerData;

import java.util.function.Supplier;

public class SyncGatePos {
    private BlockPos pos;

    public SyncGatePos(BlockPos pos) {
        this.pos = pos;
    }

    public static void encode(SyncGatePos msg, PacketBuffer buffer) {
        buffer.writeBlockPos(msg.pos);
    }

    public static SyncGatePos decode(PacketBuffer buffer) {
        return new SyncGatePos(buffer.readBlockPos());
    }

    public static void handle(SyncGatePos msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LazyOptional<PlayerData> holder = TheoraAPI.getPlayerData(Minecraft.getInstance().player);
            PlayerData data = holder.orElse(new PlayerData());
            data.setGatePos(msg.pos);
        });
        ctx.get().setPacketHandled(true);
    }
}
