package xieao.theora.network.packet.playerdata;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.PlayerData;

import java.util.Objects;
import java.util.function.Supplier;

public class SyncPlayerData {
    private NBTTagCompound compound;

    public SyncPlayerData(NBTTagCompound compound) {
        this.compound = compound;
    }

    public static void encode(SyncPlayerData msg, PacketBuffer buffer) {
        buffer.writeCompoundTag(msg.compound);
    }

    public static SyncPlayerData decode(PacketBuffer buffer) {
        return new SyncPlayerData(Objects.requireNonNull(buffer.readCompoundTag()));
    }

    public static void handle(SyncPlayerData msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LazyOptional<PlayerData> holder = TheoraAPI.getPlayerData(Minecraft.getInstance().player);
            PlayerData data = holder.orElse(new PlayerData());
            data.read(msg.compound);
        });
        ctx.get().setPacketHandled(true);
    }
}
