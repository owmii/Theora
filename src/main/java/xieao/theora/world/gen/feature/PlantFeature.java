package xieao.theora.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import xieao.theora.core.IBlocks;

import java.util.Random;

public class PlantFeature extends Feature<PlantConfig> {
    public static final PlantFeature INSTANCE = new PlantFeature();
    private static final Block[] PLANTS = new Block[]{
            IBlocks.MUSH_GLIOPHORUS,
            IBlocks.MUSH_AMANITA_MUSCARIA,
            IBlocks.MUSH_WHITE_BEECH,
            IBlocks.MUSH_WITCH_HATE,
    };

    @Override
    public boolean func_212245_a(IWorld world, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, PlantConfig config) {
        IBlockState iblockstate = PLANTS[random.nextInt(PLANTS.length)].getDefaultState();
        int i = 0;
        for (int j = 0; j < 64; ++j) {
            BlockPos blockpos = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
            if (world.isAirBlock(blockpos) && blockpos.getY() < 255 && iblockstate.isValidPosition(world, blockpos)) {
                world.setBlockState(blockpos, iblockstate, 2);
                ++i;
            }
        }
        return i > 0;
    }
}
