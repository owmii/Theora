package xieao.theora.block;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraftforge.common.extensions.IForgeBlock;
import xieao.theora.item.BlockItemBase;

public interface IBlockBase extends IForgeBlock {
    default BlockItemBase getBlockItem(Item.Properties properties) {
        return new BlockItemBase(getBlock(), properties);
    }

    @Override
    default boolean hasTileEntity(BlockState state) {
        return createTileEntity(state, null) != null;
    }

    default boolean hideGroup() {
        return false;
    }
}
