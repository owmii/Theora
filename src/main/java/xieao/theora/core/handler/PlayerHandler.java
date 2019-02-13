package xieao.theora.core.handler;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.Ability;
import xieao.theora.api.player.PlayerData;
import xieao.theora.core.network.Network;
import xieao.theora.core.network.packet.SyncAbility;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerHandler {
    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            LazyOptional<PlayerData> holder = TheoraAPI.getPlayerData(event.player);
            holder.map(playerData -> {
                Ability.Data abilityData = playerData.getAbilityData();
                abilityData.getAbilityMap().forEach((ability, compound) -> {
                    if (abilityData.enabled(ability)) {
                        ability.tick(event.player, abilityData);
                    }
                });
                if (event.side == LogicalSide.SERVER) {
                    if (abilityData.sync && event.player instanceof EntityPlayerMP) {
                        Network.toClient(new SyncAbility(abilityData.write(new NBTTagCompound())),
                                (EntityPlayerMP) event.player);
                        abilityData.sync(false);
                    }
                }
                return playerData;
            });
        }
    }
}
