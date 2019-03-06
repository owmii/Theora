package xieao.theora.core.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xieao.theora.Theora;
import xieao.theora.api.TheoraAPI;
import xieao.theora.network.packet.playerdata.SyncPlayerData;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber
public class PlayerHandler {
    private static final Set<UUID> DATA_SYNC = new HashSet<>();

    @SubscribeEvent
    public static void playerLoggedOut(PlayerLoggedOutEvent event) {
        DATA_SYNC.remove(event.getPlayer().getUniqueID());
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (event.phase == TickEvent.Phase.END) {
            if (!DATA_SYNC.contains(player.getUniqueID()) && player instanceof EntityPlayerMP) {
                TheoraAPI.getPlayerData(player).ifPresent(playerData -> {
                    Theora.NET.toClient(new SyncPlayerData(playerData.serialize()), (EntityPlayerMP) player);
                    DATA_SYNC.add(player.getUniqueID());
                });
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
