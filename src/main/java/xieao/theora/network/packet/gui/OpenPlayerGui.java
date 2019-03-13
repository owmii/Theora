package xieao.theora.network.packet.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import xieao.theora.api.TheoraAPI;
import xieao.theora.client.gui.player.GuiPlayer;

import java.util.Objects;
import java.util.function.Supplier;

public class OpenPlayerGui {
    private NBTTagCompound compound;

    public OpenPlayerGui(NBTTagCompound compound) {
        this.compound = compound;
    }

    public static void encode(OpenPlayerGui msg, PacketBuffer buffer) {
        buffer.writeCompoundTag(msg.compound);
    }

    public static OpenPlayerGui decode(PacketBuffer buffer) {
        return new OpenPlayerGui(Objects.requireNonNull(buffer.readCompoundTag()));
    }

    public static void handle(OpenPlayerGui msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            TheoraAPI.getPlayerData(mc.player).ifPresent(playerData -> {
                playerData.hor.read(msg.compound);
                mc.displayGuiScreen(new GuiPlayer());
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
