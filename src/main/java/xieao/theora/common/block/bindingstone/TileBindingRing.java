package xieao.theora.common.block.bindingstone;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import xieao.theora.common.block.TheoraBlocks;
import xieao.theora.common.block.TileInvBase;

public class TileBindingRing extends TileInvBase {

    public BlockPos centerPos = BlockPos.ORIGIN;

    @Override
    public void readNBT(NBTTagCompound nbt) {
        super.readNBT(nbt);
        NBTTagCompound tag = nbt.getCompoundTag("centerPos");
        this.centerPos = NBTUtil.getPosFromTag(tag);
    }

    @Override
    public void writeNBT(NBTTagCompound nbt) {
        super.writeNBT(nbt);
        NBTTagCompound tag = NBTUtil.createPosTag(this.centerPos);
        nbt.setTag("centerPos", tag);
    }

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
                if (getWorld().isAirBlock(pos)) {
                    getWorld().setBlockState(pos, TheoraBlocks.BINDING_STONE.getDefaultState(), 2);
                    for (int[] offset : offsets_arrs) {
                        BlockPos ringPos = getPos().add(offset[0], 0, offset[1]);
                        TileEntity tileEntity = getWorld().getTileEntity(ringPos);
                        if (tileEntity instanceof TileBindingRing) {
                            ((TileBindingRing) tileEntity).centerPos = pos;
                        }
                    }
                }
                break;
            }
        }
    }

    public void tryToDemolish() {
        if (this.centerPos != null) {
            TileEntity tileEntity = getWorld().getTileEntity(this.centerPos);
            if (!getWorld().isAirBlock(this.centerPos) && tileEntity instanceof TileBindingStone) {
                getWorld().setBlockToAir(this.centerPos);
            }
            for (int[] offset : TileBindingStone.RING_OFFSETS) {
                BlockPos ringPos = getPos().add(offset[0], 0, offset[1]);
                TileEntity tileEntity1 = getWorld().getTileEntity(ringPos);
                if (tileEntity1 instanceof TileBindingRing) {
                    TileBindingRing bindingRing = (TileBindingRing) tileEntity1;
                    bindingRing.centerPos = null;
                }
            }
        }
    }

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
}
