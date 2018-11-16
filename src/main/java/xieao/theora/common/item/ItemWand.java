package xieao.theora.common.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import xieao.theora.api.item.wand.IWand;
import xieao.theora.api.item.wand.IWandable;
import xieao.theora.common.block.orb.TileOrb;
import xieao.theora.common.lib.helper.NBTHelper;

import javax.annotation.Nullable;
import java.util.List;

public class ItemWand extends ItemBase implements IWand {

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        IBlockState state = world.getBlockState(pos);
        ItemStack held = player.getHeldItem(hand);
        TileEntity tileEntity = world.getTileEntity(pos);
        int mode = NBTHelper.getInteger(held, "mode");
        if (mode == 0) {
            if (state.getBlock() instanceof IWandable) {
                IWandable wandable = (IWandable) state.getBlock();
                wandable.performWand(world, pos, player, hand, (IWand) held.getItem(), side);
                return EnumActionResult.SUCCESS;
            }
        } else {
            if (tileEntity instanceof TileOrb) {
                TileOrb orb = (TileOrb) tileEntity;
                NBTHelper.setTag(held, "orbPos", NBTUtil.createPosTag(pos));
                return EnumActionResult.SUCCESS;
            } else {
                BlockPos orbPos = NBTUtil.getPosFromTag(NBTHelper.getCompoundTag(held, "orbPos"));
                if (orbPos != BlockPos.ORIGIN && world.isAreaLoaded(orbPos, 4)) {
                    TileEntity tileEntity1 = world.getTileEntity(orbPos);
                    if (tileEntity1 instanceof TileOrb) {
                        TileOrb orb = (TileOrb) tileEntity1;
                        if (orb.link(pos)) {
                            return EnumActionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (playerIn.isSneaking()) {
            int mode = NBTHelper.getInteger(stack, "mode");
            NBTHelper.setInteger(stack, "mode", mode == 0 ? 1 : 0);
            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        int mode = NBTHelper.getInteger(stack, "mode");
        tooltip.add("Mode: " + (mode == 0 ? "Normal" : "Link"));
        BlockPos orbPos = NBTUtil.getPosFromTag(NBTHelper.getCompoundTag(stack, "orbPos"));
        if (mode == 1 && orbPos != BlockPos.ORIGIN && !worldIn.isAreaLoaded(orbPos, 4)) {
            tooltip.add(TextFormatting.RED + "Linked Orb are in unloaded chunk!");
        }
        //TODO localisation
    }
}

