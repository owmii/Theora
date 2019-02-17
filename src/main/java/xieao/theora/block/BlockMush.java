package xieao.theora.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import xieao.theora.block.base.IBlock;
import xieao.theora.core.IBlocks;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockMush extends IBlock.Plant implements IShearable {
    @Override
    public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
        if (this == IBlocks.MUSH_GLIOPHORUS) {
            return Items.COAL;
        } else if (this == IBlocks.MUSH_WHITE_BEECH) {
            return Items.APPLE;
        } else if (this == IBlocks.MUSH_WITCH_HATE) {
            return Items.ARROW;
        } else if (this == IBlocks.MUSH_AMANITA_MUSCARIA) {
            return Items.BEETROOT_SEEDS;
        }
        return Items.AIR;
    }

    @Override
    public int quantityDropped(IBlockState state, Random random) {
        return 1 + random.nextInt(3);
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IWorld world, BlockPos pos, int fortune) {
        return Collections.singletonList(new ItemStack(this, 1));
    }
}
