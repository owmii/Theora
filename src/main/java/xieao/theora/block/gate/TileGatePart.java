package xieao.theora.block.gate;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import xieao.theora.block.TileBase;
import xieao.theora.core.ITiles;

import javax.annotation.Nullable;

public class TileGatePart extends TileBase {
    @Nullable
    private BlockPos gatePos;

    public TileGatePart() {
        super(ITiles.GATE_PART);
    }

    @Override
    public void readSync(NBTTagCompound compound) {
        super.readSync(compound);
        if (compound.contains("GatePos", Constants.NBT.TAG_COMPOUND)) {
            setGatePos(NBTUtil.readBlockPos(compound.getCompound("GatePos")));
        }
    }

    @Override
    public NBTTagCompound writeSync(NBTTagCompound compound) {
        if (this.gatePos != null) {
            compound.put("GatePos", NBTUtil.writeBlockPos(getGatePos()));
        }
        return super.writeSync(compound);
    }

    @Nullable
    public BlockPos getGatePos() {
        return gatePos;
    }

    public void setGatePos(@Nullable BlockPos gatePos) {
        this.gatePos = gatePos;
    }
}
