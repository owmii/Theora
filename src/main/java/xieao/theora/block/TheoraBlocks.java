package xieao.theora.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import xieao.lib.block.IBlock;
import xieao.theora.Theora;

import java.util.ArrayList;
import java.util.List;

public class TheoraBlocks {

    public static final List<ItemBlock> ITEM_BLOCKS = new ArrayList<>();
    public static final List<Block> BLOCKS = new ArrayList<>();

    static <T extends Block & IBlock> T register(String name, T block) {
        ItemBlock itemBlock = block.getItemBlock(new Item.Builder().group(Theora.MAIN));
        itemBlock.setRegistryName(name);
        ITEM_BLOCKS.add(itemBlock);
        block.setRegistryName(name);
        BLOCKS.add(block);
        return block;
    }
}