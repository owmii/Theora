package xieao.theora.common.block.misc;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import xieao.theora.common.block.BlockBase;
import xieao.theora.common.item.IGenericItem;
import xieao.theora.common.item.ItemBlockBase;
import xieao.theora.common.item.ItemEmber;

public class BlockEmber extends BlockBase {

    public BlockEmber() {
        super(Material.ROCK);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ItemBlockBase & IGenericItem> T getItemBlock() {
        return (T) new ItemEmber(this);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
