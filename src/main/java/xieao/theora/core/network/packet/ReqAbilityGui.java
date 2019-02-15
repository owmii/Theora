package xieao.theora.core.network.packet;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.PlayerData;
import xieao.theora.core.network.Network;

import java.util.Objects;
import java.util.function.Supplier;

public class ReqAbilityGui {
    public static <T> void handle(ReqAbilityGui msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            EntityPlayerMP playerMP = ctx.get().getSender();
            Objects.requireNonNull(playerMP);
            LazyOptional<PlayerData> holder = TheoraAPI.getPlayerData(playerMP);
            holder.map(playerData -> {
                Network.toClient(new OpenAbilityGui(playerData.write(new NBTTagCompound())), playerMP);
                return playerData;
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
