package xieao.theora.common.lib.multiblock;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import xieao.theora.common.block.TileBase;

public interface IMultiBlock<T extends TileBase & IMultiBlockBuilder> {

    Set[] getSets();

    default boolean isAllInPlace(T builder) {
        boolean flag = true;
        for (Set set : getSets()) {
            int[][] offsets = set.offsets;
            for (int[] offset : offsets) {
                BlockPos pos = builder.getPos().add(offset[0], offset[1], offset[2]);
                IBlockState state = builder.getBlockState(pos);
                if (state.equals(set.state)) {
                    TileEntity tileEntity = builder.getTileEntity(pos);
                    if (tileEntity instanceof IMultiBlockPart) {
                        IMultiBlockPart part = (IMultiBlockPart) tileEntity;
                        IMultiBlockBuilder builder1 = part.getBuilder();
                        if (builder1 != null) {
                            if (builder1.built()) {
                                flag = false;
                            }
                        }
                    } else {
                        flag = false;
                    }
                } else {
                    flag = false;
                }
            }
        }
        return flag;
    }

    class Set {

        public final IBlockState state;
        public final int[][] offsets;

        public Set(IBlockState state, int[][] offsets) {
            this.state = state;
            this.offsets = offsets;
        }
    }

}
