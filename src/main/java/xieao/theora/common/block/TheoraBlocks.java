package xieao.theora.common.block;

import net.minecraft.block.Block;

import java.util.HashSet;
import java.util.Set;

public class TheoraBlocks {

    public static final Set<Block> BLOCKS = new HashSet<>();

    public static final BlockShroom SHROOM;

    static {
        SHROOM = register(new BlockShroom(), "shroom");
    }

    private static <T extends Block & IGenericBlock> T register(T block, String name) {
        block.setRegistryName(name);
        block.setUnlocalizedName(name);
        BLOCKS.add(block);
        return block;
    }

}
