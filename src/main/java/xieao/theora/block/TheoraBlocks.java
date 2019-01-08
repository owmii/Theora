package xieao.theora.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import xieao.lib.block.IBlock;
import xieao.theora.item.TheoraItems;

import java.util.ArrayList;
import java.util.List;

public class TheoraBlocks {

    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final List<ItemBlock> ITEM_BLOCKS = new ArrayList<>();


    static <T extends Block & IBlock> T register(String name, T block) {
        ItemBlock itemBlock = block.getItemBlock(TheoraItems.BUILDER);
        itemBlock.setRegistryName(name);
        block.setRegistryName(name);
        ITEM_BLOCKS.add(itemBlock);
        BLOCKS.add(block);
        return block;
    }
}