package xieao.theora.core.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xieao.theora.Theora;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.HorData;
import xieao.theora.block.hor.TileHor;
import xieao.theora.client.gui.player.GuiPlayer;
import xieao.theora.network.packet.gui.SyncPlayerGuiStatus;
import xieao.theora.network.packet.playerdata.SyncPlayerData;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber
public class PlayerHandler {
    private static final Set<UUID> DATA_SYNC = new HashSet<>();

    @SubscribeEvent
    public static void playerLoggedOut(PlayerLoggedOutEvent event) {
        EntityPlayer player = event.getPlayer();
        World world = player.world;
        DATA_SYNC.remove(player.getUniqueID());
        TheoraAPI.getPlayerData(player).ifPresent(playerData -> {
            HorData horData = playerData.hor;
            TileEntity tileEntity = horData.getTileEntity(world.isRemote);
            if (tileEntity instanceof TileHor) {
                ((TileHor) tileEntity).setPlayer(null);
            }
            horData.getLiquidHandler().read(new NBTTagCompound());
        });
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (event.phase == TickEvent.Phase.END) {
            if (event.side == LogicalSide.SERVER) {
                if (!DATA_SYNC.contains(player.getUniqueID()) && player instanceof EntityPlayerMP) {
                    TheoraAPI.getPlayerData(player).ifPresent(data -> {
                        Theora.NET.toClient(new SyncPlayerData(data.serialize()), player);
                        DATA_SYNC.add(player.getUniqueID());
                    });
                }
            }
        } else {
            TheoraAPI.getPlayerData(player).ifPresent(data -> {
                Minecraft mc = Minecraft.getInstance();
                if (data.hor.playerGuiOpen && !(mc.currentScreen instanceof GuiPlayer)) {
                    Theora.NET.toServer(new SyncPlayerGuiStatus());
                    data.hor.setPlayerGuiOpen(false);
                }
            });
        }
    }

    @SubscribeEvent
    public static void clone(PlayerEvent.Clone event) {
        TheoraAPI.getPlayerData(event.getEntityPlayer()).ifPresent(playerData ->
                TheoraAPI.getPlayerData(event.getOriginal()).ifPresent(playerData1 ->
                        playerData.read(playerData1.serialize())));
    }
}
