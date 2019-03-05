package xieao.theora.api.player;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class GateData {
    @Nullable
    public BlockPos pos;
    public int dim;
    public long lastCheck;

    public NBTTagCompound write(NBTTagCompound compound) {
        if (this.pos != null) {
            compound.put("GatePos", NBTUtil.writeBlockPos(this.pos));
        }
        compound.putInt("GateDim", this.dim);
        compound.putLong("LastCheck", this.lastCheck);
        return compound;
    }

    public void read(NBTTagCompound compound) {
        if (compound.contains("GatePos", Constants.NBT.TAG_COMPOUND)) {
            this.pos = NBTUtil.readBlockPos(compound.getCompound("GatePos"));
        }
        this.dim = compound.getInt("GateDim");
        this.lastCheck = compound.getLong("LastCheck");
    }

    public void setPos(@Nullable BlockPos pos) {
        this.pos = pos;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public void setLastCheck(long lastCheck) {
        this.lastCheck = lastCheck;
    }
}
