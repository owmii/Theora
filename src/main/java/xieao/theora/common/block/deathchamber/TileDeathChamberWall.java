package xieao.theora.common.block.deathchamber;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import xieao.theora.common.block.TileBase;

public class TileDeathChamberWall extends TileBase {

    protected BlockPos dcPos = BlockPos.ORIGIN;

    @Override
    public void readNBT(NBTTagCompound nbt) {
        super.readNBT(nbt);
        this.dcPos = NBTUtil.getPosFromTag(nbt.getCompoundTag("dcPos"));
    }

    @Override
    public void writeNBT(NBTTagCompound nbt) {
        super.writeNBT(nbt);
        nbt.setTag("dcPos", NBTUtil.createPosTag(this.dcPos));
    }

    public boolean hasDeathChamber() {
        return this.dcPos != BlockPos.ORIGIN
                && getTileEntity(this.dcPos) instanceof TileDeathChamber;
    }

    public BlockPos getDeathChamberPos() {
        return dcPos;
    }

    public TileDeathChamberWall setDeathChamberPos(BlockPos deathChamberPos) {
        this.dcPos = deathChamberPos;
        syncNBTData();
        return this;
    }
}
