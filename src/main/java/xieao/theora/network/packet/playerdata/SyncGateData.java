package xieao.theora.network.packet.playerdata;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import xieao.theora.api.TheoraAPI;

import java.util.Objects;
import java.util.function.Supplier;

public class SyncGateData {
    private NBTTagCompound compound;

    public SyncGateData(NBTTagCompound compound) {
        this.compound = compound;
    }

    public static void encode(SyncGateData msg, PacketBuffer buffer) {
        buffer.writeCompoundTag(msg.compound);
    }

    public static SyncGateData decode(PacketBuffer buffer) {
        return new SyncGateData(Objects.requireNonNull(buffer.readCompoundTag()));
    }

    public static void handle(SyncGateData msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> TheoraAPI.getPlayerData(Minecraft.getInstance().player).ifPresent(playerData ->
                playerData.gate.read(msg.compound)));
        ctx.get().setPacketHandled(true);
    }
}
