package xieao.theora.common.lib.book;

import net.minecraft.item.ItemStack;
import xieao.theora.common.item.TheoraItems;

import java.util.ArrayList;
import java.util.List;

public class TheoraBook {

    public static final List<BookCategory> BOOK_CATEGORIES = new ArrayList<>();

    public static final BookEntry HOME = new BookEntry().setBookPages(new Page().setTitle("home"));

    public static final BookCategory CAT_ITEMS = new BookCategory("items");
    public static final BookCategory CAT_BLOCKS = new BookCategory("blocks");
    public static final BookCategory CAT_LIQUIDS = new BookCategory("liquids");
    public static final BookCategory CAT_ABILITIES = new BookCategory("abilities");
    public static final BookCategory CAT_MISC = new BookCategory("misc");

    // Items category
    public static final BookCategory SUB_CAT_SHROOM_BIT = new BookCategory(new ItemStack(TheoraItems.SHROOM_BIT));
    public static final BookCategory SUB_CAT_GLIOPHIN = new BookCategory(new ItemStack(TheoraItems.GLIOPHIN));
    public static final BookCategory SUB_CAT_WAND = new BookCategory(new ItemStack(TheoraItems.WAND));
    public static final BookCategory SUB_CAT_CLEAR_SLIME = new BookCategory(new ItemStack(TheoraItems.CLEAR_SLIME));
    public static final BookCategory SUB_CAT_SOUL_EGG = new BookCategory(new ItemStack(TheoraItems.SOUL_EGG));
    public static final BookCategory SUB_CAT_VIAL = new BookCategory(new ItemStack(TheoraItems.VIAL));
    public static final BookCategory SUB_CAT_PIG_COIN = new BookCategory(new ItemStack(TheoraItems.PIG_COIN));
    public static final BookCategory SUB_CAT_PIG_COIN_BAG = new BookCategory(new ItemStack(TheoraItems.PIG_COIN_BAG));
    public static final BookCategory SUB_CAT_WITHER_TEAR = new BookCategory(new ItemStack(TheoraItems.WITHER_TEAR));
    public static final BookCategory SUB_CAT_SLATES = new BookCategory(new ItemStack(TheoraItems.XP_SLATE));

    public static void register() {
        CAT_ITEMS.addEntry(
                new PageSubCategory(
                        SUB_CAT_SHROOM_BIT.addEntry(new PageItem().setText("shroom.bit0"), new PageText("shroom.bit1")).setName("shroom.bits"),
                        SUB_CAT_GLIOPHIN.addEntry(new PageItem().setText("gliophin0")),
                        SUB_CAT_WAND.addEntry(new PageItem().setText("wand0")),
                        SUB_CAT_CLEAR_SLIME.addEntry(new PageItem().setText("clear.slime0")),
                        SUB_CAT_SOUL_EGG.addEntry(new PageItem().setText("soul.egg0")),
                        SUB_CAT_VIAL.addEntry(new PageItem().setText("vial0")),
                        SUB_CAT_PIG_COIN.addEntry(new PageItem().setText("pig.coin0")),
                        SUB_CAT_PIG_COIN_BAG.addEntry(new PageItem().setText("pig.coin.bag0")),
                        SUB_CAT_WITHER_TEAR.addEntry(new PageItem().setText("wither.tear0")),
                        SUB_CAT_SLATES.addEntry(new PageItem().setText("empty.slate0")).setName("slates")
                )
        );

        // Main caregries
        BOOK_CATEGORIES.add(CAT_ITEMS);
        BOOK_CATEGORIES.add(CAT_BLOCKS);
        BOOK_CATEGORIES.add(CAT_LIQUIDS);
        BOOK_CATEGORIES.add(CAT_ABILITIES);
        BOOK_CATEGORIES.add(CAT_MISC);
    }
}
