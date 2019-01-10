package xieao.theora.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import xieao.lib.block.IBlock;
import xieao.theora.Theora;

import java.util.ArrayList;
import java.util.List;

public class IBlocks {

    public static final List<ItemBlock> ITEM_BLOCKS = new ArrayList<>();
    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final Block MUSH_GLIOPHORUS = register("mush_gliophorus", new BlockMush());
    public static final Block MUSH_AMANITA_MUSCARIA = register("mush_amanita_muscaria", new BlockMush());
    public static final Block MUSH_WHITE_BEECH = register("mush_white_beech", new BlockMush());
    public static final Block MUSH_WITCH_HATE = register("mush_witch_hate", new BlockMush());

    static <T extends Block & IBlock> T register(String name, T block) {
        ItemBlock itemBlock = block.getItemBlock(new Item.Builder().group(Theora.MAIN));
        itemBlock.setRegistryName(name);
        ITEM_BLOCKS.add(itemBlock);
        block.setRegistryName(name);
        BLOCKS.add(block);
        return block;
    }
}