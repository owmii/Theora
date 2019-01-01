package xieao.theora.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import xieao.lib.network.ByteBuffer;
import xieao.lib.network.IPacket;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.data.PlayerData;

import javax.annotation.Nullable;

public class PacketSyncFlight implements IPacket<PacketSyncFlight> {

    private boolean allowFlying;

    public PacketSyncFlight(boolean allowFlying) {
        this.allowFlying = allowFlying;
    }

    public PacketSyncFlight() {
    }

    @Override
    public void readBytes(ByteBuffer buf) {
        this.allowFlying = buf.readBoolean();
    }

    @Override
    public void writeBytes(ByteBuffer buf) {
        buf.writeBoolean(this.allowFlying);
    }

    @Nullable
    @Override
    public IPacket onMessage(PacketSyncFlight message, MessageContext ctx, World world, EntityPlayer player) {
        mc().addScheduledTask(() -> {
            PlayerData data = TheoraAPI.getPlayerData(player);
            if (data != null) {
                player.capabilities.allowFlying = message.allowFlying;
                data.allowFlying = message.allowFlying;
                if (!message.allowFlying) {
                    player.capabilities.isFlying = false;
                }
            }
        });
        return null;
    }

    @Override
    public Side getSide() {
        return Side.CLIENT;
    }
}
