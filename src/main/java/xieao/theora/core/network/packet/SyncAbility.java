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

public class SyncAbility {
    private NBTTagCompound compound;

    public SyncAbility(NBTTagCompound compound) {
        this.compound = compound;
    }

    public static void encode(SyncAbility msg, PacketBuffer packetBuffer) {
        packetBuffer.writeCompoundTag(msg.compound);
    }

    public static SyncAbility decode(PacketBuffer packetBuffer) {
        NBTTagCompound compound = packetBuffer.readCompoundTag();
        Objects.requireNonNull(compound);
        return new SyncAbility(compound);
    }

    public static void handle(SyncAbility msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            LazyOptional<PlayerData> holder = TheoraAPI.getPlayerData(Minecraft.getInstance().player);
            holder.map(playerData -> {
                playerData.getAbilityData().read(msg.compound);
                return playerData;
            });
        });
    }
}
