package xieao.theora.common.block;

import net.minecraft.block.Block;
import xieao.theora.common.block.fermentingjar.BlockFermentingJar;
import xieao.theora.common.block.misc.BlockShroom;

import java.util.HashSet;
import java.util.Set;

public class TheoraBlocks {

    public static final Set<Block> BLOCKS = new HashSet<>();

    public static final BlockShroom SHROOM;
    public static final BlockFermentingJar FERMENTING_JAR;

    static {
        SHROOM = register(new BlockShroom(), "shroom");
        FERMENTING_JAR = register(new BlockFermentingJar(), "fermentingjar");
    }

    private static <T extends Block & IGenericBlock> T register(T block, String name) {
        block.setRegistryName(name);
        block.setUnlocalizedName(name);
        BLOCKS.add(block);
        return block;
    }

}
