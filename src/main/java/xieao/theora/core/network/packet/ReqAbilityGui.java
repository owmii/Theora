package xieao.theora.core.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.PlayerData;
import xieao.theora.core.network.Network;

import java.util.Objects;
import java.util.function.Supplier;

public class ReqAbilityGui {
    public static <T> void handle(ReqAbilityGui msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            LazyOptional<PlayerData> holder = TheoraAPI.getPlayerData(Minecraft.getInstance().player);
            holder.map(playerData -> {
                Network.toClient(new OpenAbilityGui(playerData.write(new NBTTagCompound())),
                        Objects.requireNonNull(contextSupplier.get().getSender()));
                return playerData;
            });
        });
    }
}
