package xieao.theora.common.block.deathchamber;

import net.minecraft.util.math.BlockPos;
import xieao.theora.common.block.TileBase;

public class TileDeathChamberWall extends TileBase {

    protected BlockPos dcPos = BlockPos.ORIGIN;

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
