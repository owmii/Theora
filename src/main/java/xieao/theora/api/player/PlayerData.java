package xieao.theora.api.player;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public final class PlayerData {
    public CompoundNBT write(CompoundNBT compound) {
        return compound;
    }

    public void read(CompoundNBT compound) {
    }

    public CompoundNBT serialize() {
        return write(new CompoundNBT());
    }

    public static class Cap {
        @CapabilityInject(PlayerData.class)
        @SuppressWarnings("ConstantConditions")
        public static Capability<PlayerData> DATA = null;
    }
}
