package xieao.theora.api.liquid;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LiquidUtil {

    @Nullable
    public static IliquidContainer getILiquidContainer(World world, BlockPos pos, @Nullable EnumFacing side) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock().hasTileEntity(state)) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity != null && tileEntity.hasCapability(LiquidContainerCapability.CAPABILITY_LIQUID_CONTAINER, side)) {
                return tileEntity.getCapability(LiquidContainerCapability.CAPABILITY_LIQUID_CONTAINER, side);
            }
        }
        return null;
    }

    @Nullable
    public static IliquidContainer.Item getILiquidContainerItem(ItemStack stack) {
        if (stack.hasCapability(LiquidContainerCapability.CAPABILITY_LIQUID_CONTAINER_ITEM, null)) {
            return stack.getCapability(LiquidContainerCapability.CAPABILITY_LIQUID_CONTAINER_ITEM, null);
        }
        return null;
    }
}
