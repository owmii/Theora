package xieao.theora.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.ByteProcessor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.util.UUID;

public class ByteBuffer extends ByteBuf {

    private final ByteBuf byteBuf;

    public ByteBuffer(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    public void writeItemStack(ItemStack stack) {
        PacketBuffer pb = new PacketBuffer(getByteBuffer());
        pb.writeItemStack(stack);
    }

    public ItemStack readItemStack() {
        PacketBuffer pb = new PacketBuffer(getByteBuffer());
        try {
            return pb.readItemStack();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeBlockPos(BlockPos pos) {
        PacketBuffer pb = new PacketBuffer(getByteBuffer());
        pb.writeBlockPos(pos);
    }

    public BlockPos readBlockPos() {
        PacketBuffer pb = new PacketBuffer(getByteBuffer());
        return pb.readBlockPos();
    }

    public void writeCompoundTag(NBTTagCompound compound) {
        PacketBuffer pb = new PacketBuffer(getByteBuffer());
        pb.writeCompoundTag(compound);
    }

    @Nullable
    public NBTTagCompound readCompoundTag() {
        PacketBuffer pb = new PacketBuffer(getByteBuffer());
        try {
            return pb.readCompoundTag();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeUuid(UUID uuid) {
        writeString(uuid.toString());
    }

    public UUID readUuid() {
        return UUID.fromString(readString());
    }

    public void writeString(String string) {
        ByteBufUtils.writeUTF8String(getByteBuffer(), string);
    }

    public String readString() {
        return ByteBufUtils.readUTF8String(getByteBuffer());
    }

    public ByteBuf getByteBuffer() {
        return byteBuf;
    }

    @Override
    public int capacity() {
        return getByteBuffer().capacity();
    }

    @Override
    public ByteBuf capacity(int newCapacity) {
        return getByteBuffer().capacity(newCapacity);
    }

    @Override
    public int maxCapacity() {
        return getByteBuffer().maxCapacity();
    }

    @Override
    public ByteBufAllocator alloc() {
        return getByteBuffer().alloc();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ByteOrder order() {
        return getByteBuffer().order();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ByteBuf order(ByteOrder endianness) {
        return getByteBuffer().order(endianness);
    }

    @Override
    public ByteBuf unwrap() {
        return getByteBuffer().unwrap();
    }

    @Override
    public boolean isDirect() {
        return getByteBuffer().isDirect();
    }

    @Override
    public boolean isReadOnly() {
        return getByteBuffer().isReadOnly();
    }

    @Override
    public ByteBuf asReadOnly() {
        return getByteBuffer().asReadOnly();
    }

    @Override
    public int readerIndex() {
        return getByteBuffer().readerIndex();
    }

    @Override
    public ByteBuf readerIndex(int readerIndex) {
        return getByteBuffer().readerIndex(readerIndex);
    }

    @Override
    public int writerIndex() {
        return getByteBuffer().writerIndex();
    }

    @Override
    public ByteBuf writerIndex(int writerIndex) {
        return getByteBuffer().writerIndex(writerIndex);
    }

    @Override
    public ByteBuf setIndex(int readerIndex, int writerIndex) {
        return getByteBuffer().setIndex(readerIndex, writerIndex);
    }

    @Override
    public int readableBytes() {
        return getByteBuffer().readableBytes();
    }

    @Override
    public int writableBytes() {
        return getByteBuffer().writableBytes();
    }

    @Override
    public int maxWritableBytes() {
        return getByteBuffer().maxWritableBytes();
    }

    @Override
    public boolean isReadable() {
        return getByteBuffer().isReadable();
    }

    @Override
    public boolean isReadable(int size) {
        return getByteBuffer().isReadable(size);
    }

    @Override
    public boolean isWritable() {
        return getByteBuffer().isWritable();
    }

    @Override
    public boolean isWritable(int size) {
        return getByteBuffer().isWritable(size);
    }

    @Override
    public ByteBuf clear() {
        return getByteBuffer().clear();
    }

    @Override
    public ByteBuf markReaderIndex() {
        return getByteBuffer().markReaderIndex();
    }

    @Override
    public ByteBuf resetReaderIndex() {
        return getByteBuffer().resetReaderIndex();
    }

    @Override
    public ByteBuf markWriterIndex() {
        return getByteBuffer().markWriterIndex();
    }

    @Override
    public ByteBuf resetWriterIndex() {
        return getByteBuffer().resetWriterIndex();
    }

    @Override
    public ByteBuf discardReadBytes() {
        return getByteBuffer().discardReadBytes();
    }

    @Override
    public ByteBuf discardSomeReadBytes() {
        return getByteBuffer().discardSomeReadBytes();
    }

    @Override
    public ByteBuf ensureWritable(int minWritableBytes) {
        return getByteBuffer().ensureWritable(minWritableBytes);
    }

    @Override
    public int ensureWritable(int minWritableBytes, boolean force) {
        return getByteBuffer().ensureWritable(minWritableBytes, force);
    }

    @Override
    public boolean getBoolean(int index) {
        return getByteBuffer().getBoolean(index);
    }

    @Override
    public byte getByte(int index) {
        return getByteBuffer().getByte(index);
    }

    @Override
    public short getUnsignedByte(int index) {
        return getByteBuffer().getUnsignedByte(index);
    }

    @Override
    public short getShort(int index) {
        return getByteBuffer().getShort(index);
    }

    @Override
    public short getShortLE(int index) {
        return getByteBuffer().getShortLE(index);
    }

    @Override
    public int getUnsignedShort(int index) {
        return getByteBuffer().getUnsignedShort(index);
    }

    @Override
    public int getUnsignedShortLE(int index) {
        return getByteBuffer().getUnsignedShortLE(index);
    }

    @Override
    public int getMedium(int index) {
        return getByteBuffer().getMedium(index);
    }

    @Override
    public int getMediumLE(int index) {
        return getByteBuffer().getMediumLE(index);
    }

    @Override
    public int getUnsignedMedium(int index) {
        return getByteBuffer().getUnsignedMedium(index);
    }

    @Override
    public int getUnsignedMediumLE(int index) {
        return getByteBuffer().getUnsignedMediumLE(index);
    }

    @Override
    public int getInt(int index) {
        return getByteBuffer().getInt(index);
    }

    @Override
    public int getIntLE(int index) {
        return getByteBuffer().getIntLE(index);
    }

    @Override
    public long getUnsignedInt(int index) {
        return getByteBuffer().getUnsignedInt(index);
    }

    @Override
    public long getUnsignedIntLE(int index) {
        return getByteBuffer().getUnsignedIntLE(index);
    }

    @Override
    public long getLong(int index) {
        return getByteBuffer().getLong(index);
    }

    @Override
    public long getLongLE(int index) {
        return getByteBuffer().getLongLE(index);
    }

    @Override
    public char getChar(int index) {
        return getByteBuffer().getChar(index);
    }

    @Override
    public float getFloat(int index) {
        return getByteBuffer().getFloat(index);
    }

    @Override
    public double getDouble(int index) {
        return getByteBuffer().getDouble(index);
    }

    @Override
    public ByteBuf getBytes(int index, ByteBuf dst) {
        return getByteBuffer().getBytes(index, dst);
    }

    @Override
    public ByteBuf getBytes(int index, ByteBuf dst, int length) {
        return getByteBuffer().getBytes(index, dst, length);
    }

    @Override
    public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
        return getByteBuffer().getBytes(index, dst, dstIndex, length);
    }

    @Override
    public ByteBuf getBytes(int index, byte[] dst) {
        return getByteBuffer().getBytes(index, dst);
    }

    @Override
    public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
        return getByteBuffer().getBytes(index, dst, dstIndex, length);
    }

    @Override
    public ByteBuf getBytes(int index, java.nio.ByteBuffer dst) {
        return getByteBuffer().getBytes(index, dst);
    }

    @Override
    public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
        return getByteBuffer().getBytes(index, out, length);
    }

    @Override
    public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
        return getByteBuffer().getBytes(index, out, length);
    }

    @Override
    public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
        return getByteBuffer().getBytes(index, out, position, length);
    }

    @Override
    public CharSequence getCharSequence(int index, int length, Charset charset) {
        return getByteBuffer().getCharSequence(index, length, charset);
    }

    @Override
    public ByteBuf setBoolean(int index, boolean value) {
        return getByteBuffer().setBoolean(index, value);
    }

    @Override
    public ByteBuf setByte(int index, int value) {
        return getByteBuffer().setByte(index, value);
    }

    @Override
    public ByteBuf setShort(int index, int value) {
        return getByteBuffer().setShort(index, value);
    }

    @Override
    public ByteBuf setShortLE(int index, int value) {
        return getByteBuffer().setShortLE(index, value);
    }

    @Override
    public ByteBuf setMedium(int index, int value) {
        return getByteBuffer().setMedium(index, value);
    }

    @Override
    public ByteBuf setMediumLE(int index, int value) {
        return getByteBuffer().setShortLE(index, value);
    }

    @Override
    public ByteBuf setInt(int index, int value) {
        return getByteBuffer().setInt(index, value);
    }

    @Override
    public ByteBuf setIntLE(int index, int value) {
        return getByteBuffer().setIntLE(index, value);
    }

    @Override
    public ByteBuf setLong(int index, long value) {
        return getByteBuffer().setLong(index, value);
    }

    @Override
    public ByteBuf setLongLE(int index, long value) {
        return getByteBuffer().setLongLE(index, value);
    }

    @Override
    public ByteBuf setChar(int index, int value) {
        return getByteBuffer().setChar(index, value);
    }

    @Override
    public ByteBuf setFloat(int index, float value) {
        return getByteBuffer().setFloat(index, value);
    }

    @Override
    public ByteBuf setDouble(int index, double value) {
        return getByteBuffer().setDouble(index, value);
    }

    @Override
    public ByteBuf setBytes(int index, ByteBuf src) {
        return getByteBuffer().setBytes(index, src);
    }

    @Override
    public ByteBuf setBytes(int index, ByteBuf src, int length) {
        return getByteBuffer().setBytes(index, src, length);
    }

    @Override
    public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
        return getByteBuffer().setBytes(index, src, srcIndex, length);
    }

    @Override
    public ByteBuf setBytes(int index, byte[] src) {
        return getByteBuffer().setBytes(index, src);
    }

    @Override
    public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
        return getByteBuffer().setBytes(index, src, srcIndex, length);
    }

    @Override
    public ByteBuf setBytes(int index, java.nio.ByteBuffer src) {
        return getByteBuffer().setBytes(index, src);
    }

    @Override
    public int setBytes(int index, InputStream in, int length) throws IOException {
        return getByteBuffer().setBytes(index, in, length);
    }

    @Override
    public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
        return getByteBuffer().setBytes(index, in, length);
    }

    @Override
    public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
        return getByteBuffer().setBytes(index, in, position, length);
    }

    @Override
    public ByteBuf setZero(int index, int length) {
        return getByteBuffer().setZero(index, length);
    }

    @Override
    public int setCharSequence(int index, CharSequence sequence, Charset charset) {
        return getByteBuffer().setCharSequence(index, sequence, charset);
    }

    @Override
    public boolean readBoolean() {
        return getByteBuffer().readBoolean();
    }

    @Override
    public byte readByte() {
        return getByteBuffer().readByte();
    }

    @Override
    public short readUnsignedByte() {
        return getByteBuffer().readUnsignedByte();
    }

    @Override
    public short readShort() {
        return getByteBuffer().readShort();
    }

    @Override
    public short readShortLE() {
        return getByteBuffer().readShortLE();
    }

    @Override
    public int readUnsignedShort() {
        return getByteBuffer().readUnsignedShort();
    }

    @Override
    public int readUnsignedShortLE() {
        return getByteBuffer().readUnsignedShortLE();
    }

    @Override
    public int readMedium() {
        return getByteBuffer().readMedium();
    }

    @Override
    public int readMediumLE() {
        return getByteBuffer().readMediumLE();
    }

    @Override
    public int readUnsignedMedium() {
        return getByteBuffer().readUnsignedMedium();
    }

    @Override
    public int readUnsignedMediumLE() {
        return getByteBuffer().readUnsignedMediumLE();
    }

    @Override
    public int readInt() {
        return getByteBuffer().readInt();
    }

    @Override
    public int readIntLE() {
        return getByteBuffer().readIntLE();
    }

    @Override
    public long readUnsignedInt() {
        return getByteBuffer().readUnsignedInt();
    }

    @Override
    public long readUnsignedIntLE() {
        return getByteBuffer().readUnsignedIntLE();
    }

    @Override
    public long readLong() {
        return getByteBuffer().readLong();
    }

    @Override
    public long readLongLE() {
        return getByteBuffer().readLongLE();
    }

    @Override
    public char readChar() {
        return getByteBuffer().readChar();
    }

    @Override
    public float readFloat() {
        return getByteBuffer().readFloat();
    }

    @Override
    public double readDouble() {
        return getByteBuffer().readDouble();
    }

    @Override
    public ByteBuf readBytes(int length) {
        return getByteBuffer().readBytes(length);
    }

    @Override
    public ByteBuf readSlice(int length) {
        return getByteBuffer().readSlice(length);
    }

    @Override
    public ByteBuf readRetainedSlice(int length) {
        return getByteBuffer().readRetainedSlice(length);
    }

    @Override
    public ByteBuf readBytes(ByteBuf dst) {
        return getByteBuffer().readBytes(dst);
    }

    @Override
    public ByteBuf readBytes(ByteBuf dst, int length) {
        return getByteBuffer().readBytes(dst, length);
    }

    @Override
    public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
        return getByteBuffer().readBytes(dst, dstIndex, length);
    }

    @Override
    public ByteBuf readBytes(byte[] dst) {
        return getByteBuffer().readBytes(dst);
    }

    @Override
    public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
        return getByteBuffer().readBytes(dst, dstIndex, length);
    }

    @Override
    public ByteBuf readBytes(java.nio.ByteBuffer dst) {
        return getByteBuffer().readBytes(dst);
    }

    @Override
    public ByteBuf readBytes(OutputStream out, int length) throws IOException {
        return getByteBuffer().readBytes(out, length);
    }

    @Override
    public int readBytes(GatheringByteChannel out, int length) throws IOException {
        return getByteBuffer().readBytes(out, length);
    }

    @Override
    public CharSequence readCharSequence(int length, Charset charset) {
        return getByteBuffer().readCharSequence(length, charset);
    }

    @Override
    public int readBytes(FileChannel out, long position, int length) throws IOException {
        return getByteBuffer().readBytes(out, position, length);
    }

    @Override
    public ByteBuf skipBytes(int length) {
        return getByteBuffer().skipBytes(length);
    }

    @Override
    public ByteBuf writeBoolean(boolean value) {
        return getByteBuffer().writeBoolean(value);
    }

    @Override
    public ByteBuf writeByte(int value) {
        return getByteBuffer().writeByte(value);
    }

    @Override
    public ByteBuf writeShort(int value) {
        return getByteBuffer().writeShort(value);
    }

    @Override
    public ByteBuf writeShortLE(int value) {
        return getByteBuffer().writeShortLE(value);
    }

    @Override
    public ByteBuf writeMedium(int value) {
        return getByteBuffer().writeMedium(value);
    }

    @Override
    public ByteBuf writeMediumLE(int value) {
        return getByteBuffer().writeMediumLE(value);
    }

    @Override
    public ByteBuf writeInt(int value) {
        return getByteBuffer().writeInt(value);
    }

    @Override
    public ByteBuf writeIntLE(int value) {
        return getByteBuffer().writeIntLE(value);
    }

    @Override
    public ByteBuf writeLong(long value) {
        return getByteBuffer().writeLong(value);
    }

    @Override
    public ByteBuf writeLongLE(long value) {
        return getByteBuffer().writeLongLE(value);
    }

    @Override
    public ByteBuf writeChar(int value) {
        return getByteBuffer().writeChar(value);
    }

    @Override
    public ByteBuf writeFloat(float value) {
        return getByteBuffer().writeFloat(value);
    }

    @Override
    public ByteBuf writeDouble(double value) {
        return getByteBuffer().writeDouble(value);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf src) {
        return getByteBuffer().writeBytes(src);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf src, int length) {
        return getByteBuffer().writeBytes(src, length);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
        return getByteBuffer().writeBytes(src, srcIndex, length);
    }

    @Override
    public ByteBuf writeBytes(byte[] src) {
        return getByteBuffer().writeBytes(src);
    }

    @Override
    public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
        return getByteBuffer().writeBytes(src, srcIndex, length);
    }

    @Override
    public ByteBuf writeBytes(java.nio.ByteBuffer src) {
        return getByteBuffer().writeBytes(src);
    }

    @Override
    public int writeBytes(InputStream in, int length) throws IOException {
        return getByteBuffer().writeBytes(in, length);
    }

    @Override
    public int writeBytes(ScatteringByteChannel in, int length) throws IOException {
        return getByteBuffer().writeBytes(in, length);
    }

    @Override
    public int writeBytes(FileChannel in, long position, int length) throws IOException {
        return getByteBuffer().writeBytes(in, position, length);
    }

    @Override
    public ByteBuf writeZero(int length) {
        return getByteBuffer().writeZero(length);
    }

    @Override
    public int writeCharSequence(CharSequence sequence, Charset charset) {
        return getByteBuffer().writeCharSequence(sequence, charset);
    }

    @Override
    public int indexOf(int fromIndex, int toIndex, byte value) {
        return getByteBuffer().indexOf(fromIndex, toIndex, value);
    }

    @Override
    public int bytesBefore(byte value) {
        return getByteBuffer().bytesBefore(value);
    }

    @Override
    public int bytesBefore(int length, byte value) {
        return getByteBuffer().bytesBefore(length, value);
    }

    @Override
    public int bytesBefore(int index, int length, byte value) {
        return getByteBuffer().bytesBefore(index, length, value);
    }

    @Override
    public int forEachByte(ByteProcessor processor) {
        return getByteBuffer().forEachByte(processor);
    }

    @Override
    public int forEachByte(int index, int length, ByteProcessor processor) {
        return getByteBuffer().forEachByte(index, length, processor);
    }

    @Override
    public int forEachByteDesc(ByteProcessor processor) {
        return getByteBuffer().forEachByteDesc(processor);
    }

    @Override
    public int forEachByteDesc(int index, int length, ByteProcessor processor) {
        return getByteBuffer().forEachByteDesc(index, length, processor);
    }

    @Override
    public ByteBuf copy() {
        return getByteBuffer().copy();
    }

    @Override
    public ByteBuf copy(int index, int length) {
        return getByteBuffer().copy(index, length);
    }

    @Override
    public ByteBuf slice() {
        return getByteBuffer().slice();
    }

    @Override
    public ByteBuf retainedSlice() {
        return getByteBuffer().retainedSlice();
    }

    @Override
    public ByteBuf slice(int index, int length) {
        return getByteBuffer().slice(index, length);
    }

    @Override
    public ByteBuf retainedSlice(int index, int length) {
        return getByteBuffer().retainedSlice(index, length);
    }

    @Override
    public ByteBuf duplicate() {
        return getByteBuffer().duplicate();
    }

    @Override
    public ByteBuf retainedDuplicate() {
        return getByteBuffer().retainedDuplicate();
    }

    @Override
    public int nioBufferCount() {
        return getByteBuffer().nioBufferCount();
    }

    @Override
    public java.nio.ByteBuffer nioBuffer() {
        return getByteBuffer().nioBuffer();
    }

    @Override
    public java.nio.ByteBuffer nioBuffer(int index, int length) {
        return getByteBuffer().nioBuffer(index, length);
    }

    @Override
    public java.nio.ByteBuffer internalNioBuffer(int index, int length) {
        return getByteBuffer().internalNioBuffer(index, length);
    }

    @Override
    public java.nio.ByteBuffer[] nioBuffers() {
        return getByteBuffer().nioBuffers();
    }

    @Override
    public java.nio.ByteBuffer[] nioBuffers(int index, int length) {
        return getByteBuffer().nioBuffers(index, length);
    }

    @Override
    public boolean hasArray() {
        return getByteBuffer().hasArray();
    }

    @Override
    public byte[] array() {
        return getByteBuffer().array();
    }

    @Override
    public int arrayOffset() {
        return getByteBuffer().arrayOffset();
    }

    @Override
    public boolean hasMemoryAddress() {
        return getByteBuffer().hasMemoryAddress();
    }

    @Override
    public long memoryAddress() {
        return getByteBuffer().memoryAddress();
    }

    @Override
    public String toString(Charset charset) {
        return getByteBuffer().toString(charset);
    }

    @Override
    public String toString(int index, int length, Charset charset) {
        return getByteBuffer().toString(index, length, charset);
    }

    @Override
    public int hashCode() {
        return getByteBuffer().hashCode();
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        return getByteBuffer().equals(obj);
    }

    @Override
    public int compareTo(ByteBuf buffer) {
        return getByteBuffer().compareTo(buffer);
    }

    @Override
    public String toString() {
        return getByteBuffer().toString();
    }

    @Override
    public ByteBuf retain(int increment) {
        return getByteBuffer().retain(increment);
    }

    @Override
    public int refCnt() {
        return getByteBuffer().refCnt();
    }

    @Override
    public ByteBuf retain() {
        return getByteBuffer().retain();
    }

    @Override
    public ByteBuf touch() {
        return getByteBuffer().touch();
    }

    @Override
    public ByteBuf touch(Object hint) {
        return getByteBuffer().touch(hint);
    }

    @Override
    public boolean release() {
        return getByteBuffer().release();
    }

    @Override
    public boolean release(int decrement) {
        return getByteBuffer().release(decrement);
    }
}
