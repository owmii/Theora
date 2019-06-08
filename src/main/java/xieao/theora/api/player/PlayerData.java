package xieao.theora.api.player;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

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
        public static Capability<PlayerData> DATA = null;

        public static void register() {
            CapabilityManager.INSTANCE.register(PlayerData.class, new Capability.IStorage<PlayerData>() {
                @Nullable
                @Override
                public INBT writeNBT(Capability<PlayerData> capability, PlayerData instance, Direction side) {
                    return null;
                }

                @Override
                public void readNBT(Capability<PlayerData> capability, PlayerData instance, Direction side, INBT nbt) {
                }
            }, PlayerData::new);
        }
    }
}
