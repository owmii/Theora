package xieao.theora.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import xieao.lib.network.ByteBuffer;
import xieao.lib.network.IPacket;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.ability.Abilities;
import xieao.theora.api.player.data.PlayerData;

import javax.annotation.Nullable;

public class PacketSyncAbilities implements IPacket<PacketSyncAbilities> {

    private NBTTagCompound nbt;

    public PacketSyncAbilities(NBTTagCompound nbt) {
        this.nbt = nbt;
    }

    public PacketSyncAbilities() {
        this(new NBTTagCompound());
    }

    @Override
    public void readBytes(ByteBuffer buf) {
        NBTTagCompound tagCompound = buf.readCompoundTag();
        if (tagCompound != null) {
            this.nbt = tagCompound;
        }
    }

    @Override
    public void writeBytes(ByteBuffer buf) {
        buf.writeCompoundTag(this.nbt);
    }

    @Nullable
    @Override
    public IPacket onMessage(PacketSyncAbilities message, MessageContext ctx, World world, EntityPlayer player) {
        mc().addScheduledTask(() -> {
            PlayerData data = TheoraAPI.getPlayerData(player);
            if (data != null) {
                Abilities abilities = data.getAbilities();
                abilities.deserializeNBT(message.nbt);
            }
        });
        return null;
    }

    @Override
    public Side getSide() {
        return Side.CLIENT;
    }
}
