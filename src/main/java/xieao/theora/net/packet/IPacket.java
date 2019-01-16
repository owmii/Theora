package xieao.theora.net.packet;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface IPacket {
    <T> T decode(PacketBuffer buf);

    <T> void encode(T pkt, PacketBuffer buf);

    <T> void handle(T pkt, Supplier<NetworkEvent.Context> ctx);
}
