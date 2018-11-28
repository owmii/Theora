package xieao.theora.common.lib.book;

import net.minecraft.item.ItemStack;
import xieao.theora.common.item.TheoraItems;

import java.util.ArrayList;
import java.util.List;

public class TheoraBook {

    public static final List<BookCategory> BOOK_CATEGORIES = new ArrayList<>();

    public static final BookCategory CAT_ITEMS = new BookCategory("items");
    public static final BookCategory CAT_BLOCKS = new BookCategory("blocks");
    public static final BookCategory CAT_LIQUIDS = new BookCategory("liquids");

    // Items category
    public static final BookCategory SUB_CAT_SHROOM_BIT = new BookCategory(new ItemStack(TheoraItems.SHROOM_BIT));
    public static final BookCategory SUB_CAT_WAND = new BookCategory(new ItemStack(TheoraItems.WAND));

    static {
        CAT_ITEMS.addEntry(
                new PageSubCategory(
                        SUB_CAT_SHROOM_BIT.addEntry(
                                new PageItem().setText("shroom.bit0"),
                                new PageText("shroom.bit1")
                        ),
                        SUB_CAT_WAND.addEntry(
                                new PageItem().setText("wand0"),
                                new PageText("wand1")
                        )
                )
        );
    }

    public static void register() {
        BOOK_CATEGORIES.add(CAT_ITEMS);
        BOOK_CATEGORIES.add(CAT_BLOCKS);
        BOOK_CATEGORIES.add(CAT_LIQUIDS);
    }
}