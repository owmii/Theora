package xieao.theora.core.handler;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.Ability;
import xieao.theora.api.player.PlayerData;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerHandler {
    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
        LazyOptional<PlayerData> holder = TheoraAPI.getPlayerData(event.player);
        holder.map(playerData -> {
            Ability.Data abilityData = playerData.getAbilityData();
            abilityData.getAbilityMap().forEach((ability, compound) ->
                    ability.tick(event.player, abilityData.getAbilityNBT(ability)));
            return playerData;
        });
    }
}
