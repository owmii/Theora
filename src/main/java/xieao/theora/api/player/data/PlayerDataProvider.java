package xieao.theora.api.player.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerDataProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {

    private final PlayerData playerData = new PlayerData();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == PlayerDataCapability.PLAYER_DATA;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == PlayerDataCapability.PLAYER_DATA ? (T) this.playerData : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return this.playerData.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.playerData.deserializeNBT(nbt);
    }
}
