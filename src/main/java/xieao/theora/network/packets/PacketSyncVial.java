package xieao.theora.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.data.PlayerData;

import javax.annotation.Nullable;

public class PacketSyncVial implements IPacket<PacketSyncVial> {

    private float storedAcid;
    private boolean hasAcidVial;

    public PacketSyncVial(float storedAcid, boolean hasAcidVial) {
        this.storedAcid = storedAcid;
        this.hasAcidVial = hasAcidVial;
    }

    public PacketSyncVial(float storedAcid) {
        this(storedAcid, true);
    }

    public PacketSyncVial() {
        this(0.0F, true);
    }

    @Override
    public void writeBytes(ByteBuffer buf) {
        buf.writeFloat(this.storedAcid);
        buf.writeBoolean(this.hasAcidVial);
    }

    @Override
    public void readBytes(ByteBuffer buf) {
        this.storedAcid = buf.readFloat();
        this.hasAcidVial = buf.readBoolean();
    }

    @Nullable
    @Override
    public IPacket onMessage(PacketSyncVial message, MessageContext ctx, World world, EntityPlayer player) {
        minecraft().addScheduledTask(() -> {
            PlayerData data = TheoraAPI.getPlayerData(player);
            if (data != null) {
//                data.setHasAcidVial(message.hasAcidVial);
//                data.setStoredAcid(message.storedAcid);
            }
        });
        return null;
    }

    @Override
    public Side getSide() {
        return Side.CLIENT;
    }
}
