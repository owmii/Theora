package xieao.theora.common.block.misc;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import xieao.theora.common.block.BlockBase;
import xieao.theora.common.item.IGenericItem;
import xieao.theora.common.item.ItemBlockBase;
import xieao.theora.common.item.ItemEmber;

import javax.annotation.Nullable;

public class BlockEmber extends BlockBase {

    private static final AxisAlignedBB BB = new AxisAlignedBB(0.62D, 0.0D, 0.62D, 0.38D, 0.06D, 0.38D);

    public BlockEmber() {
        super(Material.ROCK);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BB;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return null;
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
