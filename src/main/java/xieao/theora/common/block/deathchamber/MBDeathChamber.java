package xieao.theora.common.block.deathchamber;

import xieao.theora.common.block.TheoraBlocks;
import xieao.theora.common.lib.multiblock.IMultiBlock;

public class MBDeathChamber implements IMultiBlock<TileDeathChamber> {

    public static final int[][] CHAMBER_SET_0 = new int[][]{{1, 0, 0}, {0, 0, 1}, {-1, 0, 0}, {0, 0, -1}, {1, 0, 1}, {1, 0, -1}, {-1, 0, 1}, {-1, 0, -1}};
    public static final int[][] CHAMBER_SET_1 = new int[][]{{1, 1, 0}, {0, 1, 1}, {-1, 1, 0}, {0, 1, -1}, {1, 1, 1}, {1, 1, -1}, {-1, 1, 1}, {-1, 1, -1}};
    public static final int[][] CHAMBER_SET_2 = new int[][]{{1, 2, 0}, {0, 2, 1}, {-1, 2, 0}, {0, 2, -1}, {1, 2, 1}, {1, 2, -1}, {-1, 2, 1}, {-1, 2, -1}};
    public static final int[][] CHAMBER_SET_3 = new int[][]{{0, 3, 0}, {1, 3, 0}, {0, 3, 1}, {-1, 3, 0}, {0, 3, -1}, {1, 3, 1}, {1, 3, -1}, {-1, 3, 1}, {-1, 3, -1}};

    public static final int[][] LEGS_SET_0 = new int[][]{{2, 0, 2}, {2, 0, -2}, {-2, 0, 2}, {-2, 0, -2}};
    public static final int[][] LEGS_SET_1 = new int[][]{{2, -1, 2}, {2, -1, -2}, {-2, -1, 2}, {-2, -1, -2}};
    public static final int[][] LEGS_SET_2 = new int[][]{{2, -2, 2}, {2, -2, -2}, {-2, -2, 2}, {-2, -2, -2}};

    @Override
    public Set[] getSets() {
        return new Set[]{
                new Set(TheoraBlocks.DEATH_CHAMBER_WALL.getDefaultState(), CHAMBER_SET_0),
                new Set(TheoraBlocks.DEATH_CHAMBER_WALL.getDefaultState(), CHAMBER_SET_1),
                new Set(TheoraBlocks.DEATH_CHAMBER_WALL.getDefaultState(), CHAMBER_SET_2),
                new Set(TheoraBlocks.DEATH_CHAMBER_WALL.getDefaultState(), CHAMBER_SET_3),
                new Set(TheoraBlocks.DEATH_CHAMBER_WALL.getDefaultState(), LEGS_SET_0),
                new Set(TheoraBlocks.DEATH_CHAMBER_WALL.getDefaultState(), LEGS_SET_1),
                new Set(TheoraBlocks.DEATH_CHAMBER_WALL.getDefaultState(), LEGS_SET_2)
        };
    }
}
