package xieao.theora.network.packet.playerdata;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import xieao.theora.api.TheoraAPI;

import java.util.Objects;
import java.util.function.Supplier;

public class SyncHorData {
    private NBTTagCompound compound;

    public SyncHorData(NBTTagCompound compound) {
        this.compound = compound;
    }

    public static void encode(SyncHorData msg, PacketBuffer buffer) {
        buffer.writeCompoundTag(msg.compound);
    }

    public static SyncHorData decode(PacketBuffer buffer) {
        return new SyncHorData(Objects.requireNonNull(buffer.readCompoundTag()));
    }

    public static void handle(SyncHorData msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> TheoraAPI.getPlayerData(Minecraft.getInstance().player).ifPresent(playerData ->
                playerData.hor.read(msg.compound)));
        ctx.get().setPacketHandled(true);
    }
}
