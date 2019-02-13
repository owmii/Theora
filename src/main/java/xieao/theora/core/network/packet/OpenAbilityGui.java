package xieao.theora.core.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.PlayerData;
import xieao.theora.client.gui.GuiAbilities;

import java.util.Objects;
import java.util.function.Supplier;

public class OpenAbilityGui {
    private NBTTagCompound compound;

    public OpenAbilityGui(NBTTagCompound compound) {
        this.compound = compound;
    }

    public static void encode(OpenAbilityGui msg, PacketBuffer packetBuffer) {
        packetBuffer.writeCompoundTag(msg.compound);
    }

    public static OpenAbilityGui decode(PacketBuffer packetBuffer) {
        NBTTagCompound compound = packetBuffer.readCompoundTag();
        Objects.requireNonNull(compound);
        return new OpenAbilityGui(compound);
    }

    public static void handle(OpenAbilityGui msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            LazyOptional<PlayerData> holder = TheoraAPI.getPlayerData(Minecraft.getInstance().player);
            holder.map(playerData -> {
                playerData.getAbilityData().read(msg.compound);
                return playerData;
            });
            Minecraft.getInstance().displayGuiScreen(new GuiAbilities());
        });
    }
}
