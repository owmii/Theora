package xieao.theora.common.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import xieao.theora.Theora;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.ability.Abilities;
import xieao.theora.api.player.ability.Ability;
import xieao.theora.api.player.data.PlayerData;
import xieao.theora.api.player.data.PlayerDataProvider;
import xieao.theora.network.TheoraNetwork;
import xieao.theora.network.packets.PacketSyncAbilities;

import java.util.Map;

@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(Theora.location("player.data"), new PlayerDataProvider());
        }
    }

    @SubscribeEvent
    public static void clone(PlayerEvent.Clone event) {
        PlayerData oldData = TheoraAPI.getPlayerData(event.getOriginal());
        PlayerData newData = TheoraAPI.getPlayerData(event.getEntityPlayer());
        if (oldData != null && newData != null) {
            newData.deserializeNBT(oldData.serializeNBT());
        }
    }

    @SubscribeEvent
    public static void tickPlayer(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            EntityPlayer player = event.player;
            PlayerData data = TheoraAPI.getPlayerData(player);
            if (data != null) {
                Abilities abilities = data.getAbilities();
                for (Map.Entry<Ability, NBTTagCompound> e : abilities.getAbilityMap().entrySet()) {
                    Ability ability = e.getKey();
                    NBTTagCompound nbt = e.getValue();
                    if (abilities.isActive(ability)) {
                        ability.tickAbility(player, player.world, abilities.getAbilityLevel(ability), abilities.getSubNbt(ability));
                    }
                }
                if (event.side == Side.SERVER) {
                    if (abilities.doSync() && player instanceof EntityPlayerMP) {
                        TheoraNetwork.sendToPlayer(new PacketSyncAbilities(abilities.serializeNBT()), (EntityPlayerMP) player);
                        abilities.sync(false);
                    }
                }
            }
        }
    }
}
