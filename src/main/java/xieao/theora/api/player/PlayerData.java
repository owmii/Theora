package xieao.theora.api.player;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public final class PlayerData {
    private final Ability.Data abilityData = new Ability.Data();
    public boolean canFly;

    public NBTTagCompound write(NBTTagCompound compound) {
        this.abilityData.write(compound);
        compound.putBoolean("CanFly", this.canFly);
        return compound;
    }

    public void read(NBTTagCompound compound) {
        this.abilityData.read(compound);
        this.canFly = compound.getBoolean("CanFly");
    }

    public Ability.Data getAbilityData() {
        return abilityData;
    }

    @SuppressWarnings("ConstantConditions")
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
