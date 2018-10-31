package xieao.theora.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.ability.Abilities;
import xieao.theora.api.player.data.PlayerData;
import xieao.theora.network.TheoraNetwork;

import javax.annotation.Nullable;

public class PacketRequestAbilitiesGui implements IPacket<PacketRequestAbilitiesGui> {

    @Nullable
    @Override
    public IPacket onMessage(PacketRequestAbilitiesGui message, MessageContext ctx, World world, EntityPlayer player) {
        ((WorldServer) world).addScheduledTask(() -> {
            PlayerData data = TheoraAPI.getPlayerData(player);
            if (data != null) {
                Abilities abilities = data.getAbilities();
                TheoraNetwork.sendToPlayer(new PacketOpenAbilitiesGui(player.getUniqueID(),
                        abilities.serializeNBT()), (EntityPlayerMP) player);
            }
        });
        return null;
    }

    @Override
    public Side getSide() {
        return Side.SERVER;
    }
}
