package xieao.theora.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.ability.Abilities;
import xieao.theora.api.player.data.PlayerData;
import xieao.theora.client.gui.player.GuiAbilities;

import javax.annotation.Nullable;
import java.util.UUID;

public class PacketOpenAbilitiesGui implements IPacket<PacketOpenAbilitiesGui> {

    private UUID playerUuid;
    private NBTTagCompound nbt;

    public PacketOpenAbilitiesGui(UUID playerUuid, NBTTagCompound nbt) {
        this.playerUuid = playerUuid;
        this.nbt = nbt;
    }

    public PacketOpenAbilitiesGui() {
        this(new UUID(0, 0), new NBTTagCompound());
    }

    @Override
    public void readBytes(ByteBuffer buf) {
        this.playerUuid = UUID.fromString(buf.readString());
        NBTTagCompound tagCompound = buf.readCompoundTag();
        if (tagCompound != null) {
            this.nbt = tagCompound;
        }
    }

    @Override
    public void writeBytes(ByteBuffer buf) {
        buf.writeString(this.playerUuid.toString());
        buf.writeCompoundTag(this.nbt);
    }

    @Nullable
    @Override
    public IPacket onMessage(PacketOpenAbilitiesGui message, MessageContext ctx, World world, EntityPlayer player) {
        minecraft().addScheduledTask(() -> {
            PlayerData data = TheoraAPI.getPlayerData(player);
            if (data != null) {
                Abilities abilities = data.getAbilities();
                abilities.deserializeNBT(message.nbt);
                minecraft().displayGuiScreen(new GuiAbilities(player));
            }
        });
        return null;
    }

    @Override
    public Side getSide() {
        return Side.CLIENT;
    }
}
