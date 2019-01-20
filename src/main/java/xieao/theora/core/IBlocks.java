package xieao.theora.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.ForgeRegistries;
import xieao.theora.block.BlockMush;
import xieao.theora.block.base.IBlock;
import xieao.theora.block.cauldron.BlockCauldron;
import xieao.theora.block.heat.BlockHeat;
import xieao.theora.core.lib.annotation.PreLoad;
import xieao.theora.item.IItem;

import java.util.ArrayList;
import java.util.List;

@PreLoad
public class IBlocks {
    public static final List<ItemBlock> ITEM_BLOCKS = new ArrayList<>();
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final Block MUSH_GLIOPHORUS;
    public static final Block MUSH_AMANITA_MUSCARIA;
    public static final Block MUSH_WHITE_BEECH;
    public static final Block MUSH_WITCH_HATE;
    public static final Block CAULDRON;
    public static final Block HEAT;

    static {
        MUSH_GLIOPHORUS = register("mush_gliophorus", new BlockMush());
        MUSH_AMANITA_MUSCARIA = register("mush_amanita_muscaria", new BlockMush());
        MUSH_WHITE_BEECH = register("mush_white_beech", new BlockMush());
        MUSH_WITCH_HATE = register("mush_witch_hate", new BlockMush());
        CAULDRON = register("cauldron", new BlockCauldron());
        HEAT = register("heat", new BlockHeat(24000));
    }

    static <T extends Block & IBlock> T register(String name, T block) {
        ItemBlock itemBlock = block.getItemBlock(new Item.Builder().group(IItem.MAIN));
        itemBlock.setRegistryName(name);
        ForgeRegistries.ITEMS.register(itemBlock);
        ITEM_BLOCKS.add(itemBlock);
        block.setRegistryName(name);
        ForgeRegistries.BLOCKS.register(block);
        BLOCKS.add(block);
        return block;
    }
}