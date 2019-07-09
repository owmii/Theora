package xieao.theora.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import xieao.theora.core.IBlocks;

import java.util.Random;

public class HorSaplingBlock extends SaplingBlock implements IBlockBase {
    public HorSaplingBlock(Tree p_i48337_1_, Properties properties) {
        super(p_i48337_1_, properties);
    }

    @Override
    public void grow(IWorld worldIn, BlockPos pos, BlockState state, Random rand) {
        super.grow(worldIn, pos, state, rand);
        BlockState blockState = worldIn.getBlockState(pos);
        if (blockState.getBlock() == Blocks.OAK_LOG) {
            pos = pos.add(0, 1, 0);
            if (worldIn.isAirBlock(pos.east()) && worldIn.isAirBlock(pos.west()) ||
                    worldIn.isAirBlock(pos.north()) && worldIn.isAirBlock(pos.south())) {
                worldIn.setBlockState(pos, IBlocks.HOR_LOG.getDefaultState().with(HorLogBlock.ROTATION, Direction.byHorizontalIndex(rand.nextInt(4))), 2);
            } else {
                worldIn.setBlockState(pos.down(), IBlocks.HOR_LOG.getDefaultState().with(HorLogBlock.ROTATION, Direction.byHorizontalIndex(rand.nextInt(4))), 2);
            }
        }
    }
}
