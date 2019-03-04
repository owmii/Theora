package xieao.theora.core.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xieao.theora.api.Consts;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.PlayerData;

@Mod.EventBusSubscriber
public class CapabilityHandler {
    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(Consts.MOD_ID, "player/data"), new CapabilityDispatcher.PlayerData());
        }
    }

    @SubscribeEvent
    public static void clone(PlayerEvent.Clone event) {
        LazyOptional<PlayerData> holder = TheoraAPI.getPlayerData(event.getEntityPlayer());
        LazyOptional<PlayerData> holder1 = TheoraAPI.getPlayerData(event.getOriginal());
        PlayerData data = holder.orElse(new PlayerData());
        PlayerData data1 = holder1.orElse(new PlayerData());
        data.read(data1.write(new NBTTagCompound()));
    }
}
