package xieao.theora.core.handler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityDispatcher {
    public static class PlayerData implements ICapabilitySerializable<NBTTagCompound> {
        private final xieao.theora.api.player.PlayerData data = new xieao.theora.api.player.PlayerData();

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
    }
}
