package xieao.theora.core.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xieao.theora.Theora;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.PlayerData;
import xieao.theora.network.packet.playerdata.SyncPlayerData;

import java.util.HashSet;
import java.util.UUID;

@Mod.EventBusSubscriber
public class PlayerHandler {
    private static final HashSet<UUID> DATA_SYNC = new HashSet<>();

    @SubscribeEvent
    public static void playerLoggedOut(PlayerLoggedOutEvent event) {
        DATA_SYNC.remove(event.getPlayer().getUniqueID());
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (event.phase == TickEvent.Phase.END) {
            if (!DATA_SYNC.contains(player.getUniqueID()) && player instanceof EntityPlayerMP) {
                LazyOptional<PlayerData> holder = TheoraAPI.getPlayerData(player);
                Theora.NET.toClient(new SyncPlayerData(holder.orElse(new PlayerData())
                        .write(new NBTTagCompound())), (EntityPlayerMP) player);
                DATA_SYNC.add(player.getUniqueID());
            }
        }
    }

    @SubscribeEvent
    public static void clone(PlayerEvent.Clone event) {
        LazyOptional<PlayerData> holder = TheoraAPI.getPlayerData(event.getEntityPlayer());
        PlayerData data = holder.orElse(new PlayerData());
        LazyOptional<PlayerData> holder1 = TheoraAPI.getPlayerData(event.getOriginal());
        PlayerData data1 = holder1.orElse(new PlayerData());
        data.read(data1.write(new NBTTagCompound()));
    }
}
