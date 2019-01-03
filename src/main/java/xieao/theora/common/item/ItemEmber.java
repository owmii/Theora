package xieao.theora.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.lib.item.ItemBlockBase;
import xieao.theora.common.TheoraSounds;
import xieao.theora.common.block.IHeatedBlock;

public class ItemEmber extends ItemBlockBase {

    public ItemEmber(Block block) {
        super(block);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof IHeatedBlock) {
            IHeatedBlock heatedBlock = (IHeatedBlock) state.getBlock();
            if (!heatedBlock.heated(world, pos)) {
                ItemStack stack = player.getHeldItem(hand);
                int meta = stack.getMetadata();
                IBlockState state1 = getBlock().getStateFromMeta(meta);
                heatedBlock.setHeated(world, pos, state1);
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                world.playSound(null, pos, TheoraSounds.WHOOSH_SMOKE, SoundCategory.BLOCKS, 0.5F, 1.4F);
                return EnumActionResult.SUCCESS;
            }
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }
}
