package xieao.theora.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.UUID;

public class ByteBuffer extends PacketBuffer {

    private final ByteBuf byteBuf;

    public ByteBuffer(ByteBuf byteBuf) {
        super(byteBuf);
        this.byteBuf = byteBuf;
    }

    public ByteBuffer writeUuid(UUID uuid) {
        writeString(uuid.toString());
        return this;
    }

    public UUID readUuid() {
        return UUID.fromString(readString());
    }

    public ByteBuffer writeString(String string) {
        ByteBufUtils.writeUTF8String(getByteBuffer(), string);
        return this;
    }

    public String readString() {
        return ByteBufUtils.readUTF8String(getByteBuffer());
    }

    public ByteBuf getByteBuffer() {
        return byteBuf;
    }
}
