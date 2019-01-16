package xieao.theora.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import xieao.theora.block.cauldron.BlockCauldron;
import xieao.theora.block.heat.BlockHeat;
import xieao.theora.item.IItem;

import java.util.ArrayList;
import java.util.List;

public class IBlocks {

    public static final List<ItemBlock> ITEM_BLOCKS = new ArrayList<>();
    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final Block MUSH_GLIOPHORUS = register("mush_gliophorus", new BlockMush());
    public static final Block MUSH_AMANITA_MUSCARIA = register("mush_amanita_muscaria", new BlockMush());
    public static final Block MUSH_WHITE_BEECH = register("mush_white_beech", new BlockMush());
    public static final Block MUSH_WITCH_HATE = register("mush_witch_hate", new BlockMush());
    public static final Block CAULDRON = register("cauldron", new BlockCauldron());
    public static final Block HEAT = register("heat", new BlockHeat(24000));

    static <T extends Block & IBlock> T register(String name, T block) {
        ItemBlock itemBlock = block.getItemBlock(new Item.Builder().group(IItem.MAIN));
        itemBlock.setRegistryName(name);
        ITEM_BLOCKS.add(itemBlock);
        block.setRegistryName(name);
        BLOCKS.add(block);
        return block;
    }
}