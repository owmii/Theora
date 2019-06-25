package xieao.theora.block.cauldron;

import net.minecraft.block.BlockState;
import xieao.theora.block.BlockBase;

public class CauldronBlock extends BlockBase {
    public CauldronBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }
}
