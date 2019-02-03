package xieao.theora.core.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.PlayerData;
import xieao.theora.core.lib.util.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityHandler {
    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(Location.ROOT.get("player/data"), new ICapabilitySerializable<NBTTagCompound>() {
                private final PlayerData data = new PlayerData();

                @Override
                @SuppressWarnings("unchecked")
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing side) {
                    return LazyOptional.of(() -> (T) this.data);
                }

                @Override
                public NBTTagCompound serializeNBT() {
                    return this.data.write(new NBTTagCompound());
                }

                @Override
                public void deserializeNBT(NBTTagCompound nbt) {
                    this.data.read(nbt);
                }
            });
        }
    }

    @SubscribeEvent
    public static void clone(PlayerEvent.Clone event) {
        TheoraAPI.getPlayerData(event.getEntityPlayer()).map(playerData -> {
            playerData.read(TheoraAPI.getPlayerData(event.getOriginal())
                    .orElse(new PlayerData())
                    .write(new NBTTagCompound()));
            return playerData;
        });
    }
}
