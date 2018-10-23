package xieao.theora.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public interface IPacket<M extends IPacket> extends IMessage, IMessageHandler<M, IPacket> {

    default void writeBytes(ByteBuffer buf) {

    }

    default void readBytes(ByteBuffer buf) {

    }

    @Override
    default void fromBytes(ByteBuf buf) {
        ByteBuffer byteBuffer = new ByteBuffer(buf);
        readBytes(byteBuffer);
    }

    @Override
    default void toBytes(ByteBuf buf) {
        ByteBuffer byteBuffer = new ByteBuffer(buf);
        writeBytes(byteBuffer);
    }

    @Nullable
    @Override
    default IPacket onMessage(M message, MessageContext ctx) {
        EntityPlayer player = getSide().isClient() ? minecraft().player : ctx.getServerHandler().player;
        return onMessage(message, ctx, player.getEntityWorld(), player);
    }

    @Nullable
    IPacket onMessage(M message, MessageContext ctx, World world, EntityPlayer player);

    Side getSide();

    @SideOnly(Side.CLIENT)
    default Minecraft minecraft() {
        return Minecraft.getMinecraft();
    }
}
