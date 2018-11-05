package xieao.theora.api.item.wand;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface IWandable {

    boolean performWand(World world, BlockPos pos, EntityPlayer player, EnumHand hand, IWand wand, @Nullable EnumFacing facing);
}
