package xieao.theora.item;

import net.minecraft.item.Item;
import xieao.theora.Theora;
import xieao.theora.block.IBlocks;

import java.util.ArrayList;
import java.util.List;

public class IItems {
    public static final List<Item> ITEMS = new ArrayList<>(IBlocks.ITEM_BLOCKS);
    public static final Item VIAL = register("vial", new ItemVial(new Item.Builder().group(Theora.MAIN)));

    static <T extends Item & IItem> T register(String name, T item) {
        item.setRegistryName(name);
        ITEMS.add(item);
        return item;
    }
}
