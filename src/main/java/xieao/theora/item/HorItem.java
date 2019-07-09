package xieao.theora.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xieao.theora.core.IBlocks;

public class HorItem extends ItemBase {
    public HorItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (entity.isInWater())
            entity.setMotion(new Vec3d(0.0D, -0.1D, 0.0D));
        if (stack.getCount() == 4) {
            World world = entity.world;
            BlockPos pos = entity.getPosition();
            BlockState state = world.getBlockState(pos);
            if (state.equals(Blocks.WATER.getDefaultState())) {
                world.setBlockState(pos, IBlocks.GLIOPHIN.getDefaultState(), 2);
                entity.remove();
            }
        }
        return false;
    }
}
