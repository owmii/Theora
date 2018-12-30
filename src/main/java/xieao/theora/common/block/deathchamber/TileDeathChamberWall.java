package xieao.theora.common.block.deathchamber;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import xieao.lib.block.TileBase;
import xieao.theora.common.lib.multiblock.IMultiBlockBuilder;
import xieao.theora.common.lib.multiblock.IMultiBlockPart;

import javax.annotation.Nullable;

public class TileDeathChamberWall extends TileBase implements IMultiBlockPart {

    @Nullable
    protected BlockPos builderPos;

    @Override
    public void readNBT(NBTTagCompound nbt) {
        super.readNBT(nbt);
        this.builderPos = NBTUtil.getPosFromTag(nbt.getCompoundTag("builderPos"));
    }

    @Override
    public void writeNBT(NBTTagCompound nbt) {
        super.writeNBT(nbt);
        if (this.builderPos != null) {
            nbt.setTag("builderPos", NBTUtil.createPosTag(this.builderPos));
        }
    }

    @Nullable
    @Override
    public BlockPos getBuilderPos() {
        return builderPos;
    }

    @Override
    public void setBuilderPos(@Nullable BlockPos pos) {
        this.builderPos = pos;
        markDirtyAndSync();
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T extends TileBase & IMultiBlockBuilder> T getBuilder() {
        if (getBuilderPos() != null) {
            TileEntity tileEntity = getTileEntity(getBuilderPos());
            if (tileEntity instanceof TileDeathChamber) {
                return (T) tileEntity;
            }
        }
        return null;
    }
}
