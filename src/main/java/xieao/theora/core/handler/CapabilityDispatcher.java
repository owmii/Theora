package xieao.theora.core.handler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import xieao.theora.api.player.PlayerData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityDispatcher {
    public static class Player implements ICapabilitySerializable<NBTTagCompound> {
        private final PlayerData data = new PlayerData();
        private final LazyOptional<PlayerData> holder = LazyOptional.of(() -> this.data);

        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing side) {
            return PlayerData.Cap.DATA.orEmpty(cap, this.holder);
        }

        @Override
        public NBTTagCompound serializeNBT() {
            return this.data.write(new NBTTagCompound());
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            this.data.read(nbt);
        }
    }
}
