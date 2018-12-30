package xieao.theora.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
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
import xieao.theora.common.block.IHeatedBlock;
import xieao.theora.common.block.TheoraBlocks;

public class ItemCauldron extends ItemBlockBase {

    public ItemCauldron(Block block) {
        super(block);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        IBlockState state = world.getBlockState(pos);
        IBlockState stateUp = world.getBlockState(pos.up());
        if (state.getBlock() == TheoraBlocks.EMBER || stateUp.getBlock() == TheoraBlocks.EMBER) {
            if (state.getBlock() != TheoraBlocks.EMBER && stateUp.getBlock() == TheoraBlocks.EMBER) {
                pos = pos.up();
            }
            ItemStack itemstack = player.getHeldItem(hand);
            if (!itemstack.isEmpty() && player.canPlayerEdit(pos, side, itemstack)) {
                int i = getMetadata(itemstack.getMetadata());
                IBlockState iblockstate1 = this.block.getStateForPlacement(world, pos, side, hitX, hitY, hitZ, i, player, hand);
                if (placeBlockAt(itemstack, player, world, pos, side, hitX, hitY, hitZ, iblockstate1)) {
                    iblockstate1 = world.getBlockState(pos);
                    SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, world, pos, player);
                    world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                }
                if (iblockstate1.getBlock() instanceof IHeatedBlock) {
                    IHeatedBlock heatedBlock = (IHeatedBlock) iblockstate1.getBlock();
                    if (!heatedBlock.heated(world, pos)) {
                        heatedBlock.setHeated(world, pos, state.getBlock() == TheoraBlocks.EMBER ? state : stateUp);
                    }
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }

}
