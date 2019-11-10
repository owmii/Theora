package zeroneye.theora.world.gen;

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
import zeroneye.theora.block.HorLogBlock;
import zeroneye.theora.block.IBlocks;

import java.util.Random;
import java.util.function.Function;

public class WoodFeature extends Feature<NoFeatureConfig> {
    public WoodFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == Blocks.OAK_LOG) {
            while (world.getBlockState(pos).getBlock() == Blocks.OAK_LOG) {
                pos = pos.up();
            }
            if (world.getBlockState(pos).getMaterial() != Material.LEAVES) {
                return false;
            }
            pos = pos.down();
            while (world.getBlockState(pos).getBlock() == Blocks.OAK_LOG) {
                pos = pos.down();
            }
            if (!Blocks.GRASS.isValidPosition(world.getBlockState(pos), world, pos)) {
                return false;
            }
            pos = pos.add(0, 2, 0);
            if (world.isAirBlock(pos.east()) || world.isAirBlock(pos.west()) ||
                    world.isAirBlock(pos.north()) || world.isAirBlock(pos.south())) {
                world.setBlockState(pos, IBlocks.HOR_LOG.getDefaultState().with(HorLogBlock.ROTATION, Direction.byHorizontalIndex(rand.nextInt(4))), 2);
            } else {
                world.setBlockState(pos.down(), IBlocks.HOR_LOG.getDefaultState().with(HorLogBlock.ROTATION, Direction.byHorizontalIndex(rand.nextInt(4))), 2);
            }
        }
        return false;
    }
}
