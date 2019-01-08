package xieao.theora.item;

import net.minecraft.item.Item;
import xieao.lib.item.IItem;
import xieao.theora.Theora;
import xieao.theora.block.TheoraBlocks;

import java.util.ArrayList;
import java.util.List;

public class TheoraItems {

    public static final List<Item> ITEMS = new ArrayList<>(TheoraBlocks.ITEM_BLOCKS);
    public static final Item.Builder MAIN = new Item.Builder().group(Theora.MAIN);


    static <T extends Item & IItem> T register(String name, T item) {
        item.setRegistryName(name);
        ITEMS.add(item);
        return item;
    }
}
