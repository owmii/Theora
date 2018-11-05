package xieao.theora.common.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.api.item.wand.IWand;
import xieao.theora.api.item.wand.IWandable;

public class ItemWand extends ItemBase implements IWand {

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        IBlockState state = world.getBlockState(pos);
        ItemStack held = player.getHeldItem(hand);
        if (state.getBlock() instanceof IWandable) {
            IWandable wandable = (IWandable) state.getBlock();
            wandable.performWand(world, pos, player, hand, (IWand) held.getItem(), side);
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }
}
