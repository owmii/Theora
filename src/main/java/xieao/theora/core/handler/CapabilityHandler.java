package xieao.theora.core.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xieao.theora.api.Consts;
import xieao.theora.api.player.PlayerData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CapabilityHandler {
    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(Consts.MOD_ID, "player/data"), new PlayerDataProvider());
        }
    }

    public static class PlayerDataProvider implements ICapabilitySerializable<NBTTagCompound> {
        private final PlayerData data = new PlayerData();
        private final LazyOptional<PlayerData> holder = LazyOptional.of(() -> this.data);

        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing side) {
            return PlayerData.Cap.DATA.orEmpty(cap, this.holder);
        }

        @Override
        public NBTTagCompound serializeNBT() {
            return this.data.serialize();
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            this.data.read(nbt);
        }
    }
}
