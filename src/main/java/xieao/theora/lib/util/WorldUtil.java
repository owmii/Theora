package xieao.theora.lib.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldUtil {
    public static boolean canSeeSkyOrBedrock(World world, BlockPos pos) {
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
        for (int y = pos.getY() + 1; y < world.getHeight(); ++y) {
            IBlockState iblockstate = world.getBlockState(blockPos.setPos(pos.getX(), y, pos.getZ()));
            Block block = iblockstate.getBlock();
            if (!world.isAirBlock(pos) && block != Blocks.BEDROCK) {
                return false;
            }
        }
        return true;
    }
}
