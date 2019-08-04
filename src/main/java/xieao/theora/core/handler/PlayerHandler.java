package xieao.theora.core.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xieao.lib.Lollipop;
import xieao.theora.api.TheoraAPI;
import xieao.theora.network.packet.SyncPlayerData;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber
public class PlayerHandler {
    private static final Set<UUID> TMP_DATA_SYNC = new HashSet<>();

    @SubscribeEvent
    public static void playerLoggedOut(PlayerLoggedOutEvent event) {
        PlayerEntity player = event.getPlayer();
        TMP_DATA_SYNC.remove(player.getUniqueID());
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (event.phase == TickEvent.Phase.END) {
            if (event.side == LogicalSide.SERVER) {
                if (!TMP_DATA_SYNC.contains(player.getUniqueID()) && player instanceof ServerPlayerEntity) {
                    TheoraAPI.getPlayerData(player).ifPresent(data -> {
                        Lollipop.NET.toClient(new SyncPlayerData(data.serialize()), player);
                        TMP_DATA_SYNC.add(player.getUniqueID());
                    });
                }
            }
        }
    }

    @SubscribeEvent
    public static void clone(PlayerEvent.Clone event) {
        TheoraAPI.getPlayerData(event.getEntityPlayer()).ifPresent(playerData ->
                TheoraAPI.getPlayerData(event.getOriginal()).ifPresent(playerData1 ->
                        playerData.read(playerData1.serialize())));
    }
}
