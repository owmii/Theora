package xieao.theora.common.block.deathchamber;

import net.minecraft.util.ITickable;
import xieao.theora.common.block.TileInvBase;

public class TileDeathChamber extends TileInvBase implements ITickable {

    @Override
    public void update() {
        if (isServerWorld()) {

        }
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }
}
