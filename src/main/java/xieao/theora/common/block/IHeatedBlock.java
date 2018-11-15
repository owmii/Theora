package xieao.theora.common.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHeatedBlock {

    boolean heated(World world, BlockPos pos);

    void setHeated(World world, BlockPos pos, IBlockState heat);
}
