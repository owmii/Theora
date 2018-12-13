package xieao.theora.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import javax.annotation.Nullable;
import java.io.IOException;

public class ByteBuffer extends PacketBuffer {

    private final ByteBuf byteBuf;

    public ByteBuffer(ByteBuf byteBuf) {
        super(byteBuf);
        this.byteBuf = byteBuf;
    }

    @Nullable
    @Override
    public NBTTagCompound readCompoundTag() {
        try {
            return super.readCompoundTag();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
