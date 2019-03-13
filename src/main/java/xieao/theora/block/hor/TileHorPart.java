package xieao.theora.block.hor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import xieao.theora.block.TileBase;
import xieao.theora.core.ITiles;

import javax.annotation.Nullable;

public class TileHorPart extends TileBase {
    @Nullable
    private BlockPos horPos;

    public TileHorPart() {
        super(ITiles.HOR_PART);
    }

    @Override
    public void readSync(NBTTagCompound compound) {
        super.readSync(compound);
        if (compound.contains("GatePos", Constants.NBT.TAG_COMPOUND)) {
            setHorPos(NBTUtil.readBlockPos(compound.getCompound("GatePos")));
        }
    }

    @Override
    public NBTTagCompound writeSync(NBTTagCompound compound) {
        if (this.horPos != null) {
            compound.put("GatePos", NBTUtil.writeBlockPos(getHorPos()));
        }
        return super.writeSync(compound);
    }

    @Nullable
    public BlockPos getHorPos() {
        return horPos;
    }

    public void setHorPos(@Nullable BlockPos horPos) {
        this.horPos = horPos;
    }
}
