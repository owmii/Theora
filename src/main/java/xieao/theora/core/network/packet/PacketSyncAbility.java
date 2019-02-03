package xieao.theora.core.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.PlayerData;

import java.util.Objects;
import java.util.function.Supplier;

public class PacketSyncAbility {
    private NBTTagCompound compound;

    public PacketSyncAbility(NBTTagCompound compound) {
        this.compound = compound;
    }

    public static void encode(PacketSyncAbility msg, PacketBuffer packetBuffer) {
        packetBuffer.writeCompoundTag(msg.compound);
    }

    public static PacketSyncAbility decode(PacketBuffer packetBuffer) {
        NBTTagCompound compound = packetBuffer.readCompoundTag();
        Objects.requireNonNull(compound);
        return new PacketSyncAbility(compound);
    }

    public static void handle(PacketSyncAbility msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            LazyOptional<PlayerData> holder = TheoraAPI.getPlayerData(Minecraft.getInstance().player);
            holder.map(playerData -> {
                playerData.getAbilityData().read(msg.compound);
                return playerData;
            });
        });
    }
}
