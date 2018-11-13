package xieao.theora.common.block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;

public abstract class BlockPlant extends BlockBush implements IGenericBlock {

    public BlockPlant() {
        setSoundType(SoundType.PLANT);
    }
}
