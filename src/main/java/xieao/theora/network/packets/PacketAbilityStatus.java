package xieao.theora.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import xieao.lib.network.ByteBuffer;
import xieao.lib.network.IPacket;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.ability.Abilities;
import xieao.theora.api.player.ability.Ability;
import xieao.theora.api.player.data.PlayerData;

import javax.annotation.Nullable;

public class PacketAbilityStatus implements IPacket<PacketAbilityStatus> {

    private Ability ability;
    private boolean active;

    public PacketAbilityStatus(Ability ability, boolean active) {
        this.ability = ability;
        this.active = active;
    }

    public PacketAbilityStatus() {
        this(Ability.EMPTY, false);
    }

    @Override
    public void readBytes(ByteBuffer buf) {
        this.ability = Ability.getAbility(buf.readString());
        this.active = buf.readBoolean();
    }

    @Override
    public void writeBytes(ByteBuffer buf) {
        buf.writeString(this.ability.getRegistryString());
        buf.writeBoolean(this.active);
    }

    @Nullable
    @Override
    public IPacket onMessage(PacketAbilityStatus message, MessageContext ctx, World world, EntityPlayer player) {
        ((WorldServer) world).addScheduledTask(() -> {
            PlayerData data = TheoraAPI.getPlayerData(player);
            if (data != null) {
                Abilities abilities = data.getAbilities();
                abilities.setAbilityStatus(message.ability, message.active);
                abilities.sync(true);
            }
        });
        return null;
    }

    @Override
    public Side getSide() {
        return Side.SERVER;
    }
}
