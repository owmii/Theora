package xieao.theora.common.block.bindingstone;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import xieao.theora.common.block.TheoraBlocks;
import xieao.theora.common.block.TileInvBase;

import javax.annotation.Nullable;

public class TileBindingRing extends TileInvBase {

    public static final int[][] CENTR_OFFSETS = {{2, -2}, {0, -3}, {-2, -2}, {-3, 0}, {-2, 2}, {0, 3}, {2, 2}, {3, 0}};

    public static final int[][][] RINGS_OFFSETS = {
            {{0, 0}, {2, 1}, {4, 0}, {5, -2}, {4, -4}, {2, -5}, {0, -4}, {-1, -2}},
            {{0, 0}, {2, -1}, {3, -3}, {2, -5}, {0, -6}, {-2, -5}, {-3, -3}, {-2, -1}},
            {{0, 0}, {1, -2}, {0, -4}, {-2, -5}, {-4, -4}, {-5, -2}, {-4, 0}, {-2, 1}},
            {{0, 0}, {-1, -2}, {-3, -3}, {-5, -2}, {-6, 0}, {-5, 2}, {-3, 3}, {-1, 2}},

            {{0, 0}, {-2, -1}, {-4, 0}, {-5, 2}, {-4, 4}, {-2, 5}, {0, 4}, {1, 2}},
            {{0, 0}, {-2, 1}, {-3, 3}, {-2, 5}, {0, 6}, {2, 5}, {3, 3}, {2, 1}},
            {{0, 0}, {-1, 2}, {0, 4}, {2, 5}, {4, 4}, {5, 2}, {4, 0}, {2, -1}},
            {{0, 0}, {1, 2}, {3, 3}, {5, 2}, {6, 0}, {5, -2}, {3, -3}, {1, -2}}
    };

    @Nullable
    public BlockPos centerPos;

    @Nullable
    public BlockPos callerPos;

    @Override
    public int getSizeInventory() {
        return 1;
    }

    public void tryToBuild() {
        for (int i = 0; i < RINGS_OFFSETS.length; i++) {
            int[][] offsets_arrs = RINGS_OFFSETS[i];
            boolean flag = false;
            for (int[] offset : offsets_arrs) {
                BlockPos ringPos = getPos().add(offset[0], 0, offset[1]);
                TileEntity tileEntity = getWorld().getTileEntity(ringPos);
                if (!(tileEntity instanceof TileBindingRing)) {
                    flag = true;
                }
            }
            if (!flag) {
                int[] arrs = CENTR_OFFSETS[i];
                BlockPos pos = getPos().add(arrs[0], 0, arrs[1]);
                if (world.isAirBlock(pos))
                    world.setBlockState(pos, TheoraBlocks.BINDING_STONE.getDefaultState(), 2);
                break;
            }
        }
    }
}
