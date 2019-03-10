package xieao.theora.api.player;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public final class PlayerData {
    public final GateData gate = new GateData();

    public NBTTagCompound write(NBTTagCompound compound) {
        this.gate.write(compound);
        return compound;
    }

    public void read(NBTTagCompound compound) {
        this.gate.read(compound);
    }

    public NBTTagCompound serialize() {
        return write(new NBTTagCompound());
    }

    public static class Cap {
        @CapabilityInject(PlayerData.class)
        public static Capability<PlayerData> DATA = null;

        public static void register() {
            CapabilityManager.INSTANCE.register(PlayerData.class, new Capability.IStorage<PlayerData>() {
                @Nullable
                @Override
                public INBTBase writeNBT(Capability<PlayerData> capability, PlayerData instance, EnumFacing side) {
                    return null;
                }

                @Override
                public void readNBT(Capability<PlayerData> capability, PlayerData instance, EnumFacing side, INBTBase nbt) {
                }
            }, PlayerData::new);
        }
    }
}
