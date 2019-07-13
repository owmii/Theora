package xieao.theora.world.gen;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import xieao.theora.block.HorLogBlock;
import xieao.theora.core.IBlocks;

import java.util.Random;
import java.util.function.Function;

public class WoodFeature extends Feature<NoFeatureConfig> {
    public WoodFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockState state = worldIn.getBlockState(pos);
        if (state.getBlock() == Blocks.OAK_LOG) {
            while (worldIn.getBlockState(pos).getBlock() == Blocks.OAK_LOG) {
                pos = pos.up();
            }
            if (worldIn.getBlockState(pos).getMaterial() != Material.LEAVES) {
                return false;
            }
            pos = pos.down();
            while (worldIn.getBlockState(pos).getBlock() == Blocks.OAK_LOG) {
                pos = pos.down();
            }
            if (!Blocks.GRASS.isValidPosition(worldIn.getBlockState(pos), worldIn, pos)) {
                return false;
            }
            pos = pos.add(0, 2, 0);
            if (worldIn.isAirBlock(pos.east()) && worldIn.isAirBlock(pos.west()) ||
                    worldIn.isAirBlock(pos.north()) && worldIn.isAirBlock(pos.south())) {
                worldIn.setBlockState(pos, IBlocks.HOR_LOG.getDefaultState().with(HorLogBlock.ROTATION, Direction.byHorizontalIndex(rand.nextInt(4))), 2);
            } else {
                worldIn.setBlockState(pos.down(), IBlocks.HOR_LOG.getDefaultState().with(HorLogBlock.ROTATION, Direction.byHorizontalIndex(rand.nextInt(4))), 2);
            }
        }
        return false;
    }
}
