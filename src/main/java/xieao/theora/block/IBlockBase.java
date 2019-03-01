package xieao.theora.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraftforge.common.extensions.IForgeBlock;
import xieao.theora.item.ItemBlockBase;

public interface IBlockBase extends IForgeBlock {
    default ItemBlockBase getItemBlock(Item.Properties properties) {
        return new ItemBlockBase(getBlock(), properties);
    }

    @Override
    default boolean hasTileEntity(IBlockState state) {
        return createTileEntity(state, null) != null;
    }
}
