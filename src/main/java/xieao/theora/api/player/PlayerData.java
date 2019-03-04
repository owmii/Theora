package xieao.theora.api.player;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public final class PlayerData {
    @Nullable
    private BlockPos gatePos;

    public NBTTagCompound write(NBTTagCompound compound) {
        if (this.gatePos != null) {
            compound.put("GatePos", NBTUtil.writeBlockPos(this.gatePos));
        }
        return compound;
    }

    public void read(NBTTagCompound compound) {
        if (compound.contains("GatePos", Constants.NBT.TAG_COMPOUND)) {
            this.gatePos = NBTUtil.readBlockPos(compound.getCompound("GatePos"));
        }
    }

    @Nullable
    public BlockPos getGatePos() {
        return gatePos;
    }

    public void setGatePos(@Nullable BlockPos gatePos) {
        this.gatePos = gatePos;
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
