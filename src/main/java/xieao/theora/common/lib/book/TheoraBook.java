package xieao.theora.common.lib.book;

import net.minecraft.item.ItemStack;
import xieao.theora.common.item.TheoraItems;
import xieao.theora.common.lib.book.page.Page;
import xieao.theora.common.lib.book.page.PageButtons;
import xieao.theora.common.lib.book.page.PageItem;
import xieao.theora.common.lib.book.page.PageText;

import java.util.ArrayList;
import java.util.List;

public class TheoraBook {

    public static final List<Section> MAIN_SECTIONS = new ArrayList<>();

    public static final Section GETTING_STARTED = new Section("getting.started");
    public static final Section ITEMS = new Section("items");
    public static final Section BLOCKS = new Section("blocks");
    public static final Section LIQUIDS = new Section("liquids");
    public static final Section ABILITIES = new Section("abilities");
    public static final Section MISC = new Section("misc");

    public static final Entry HOME = new Entry().setPages(new Page().setTitle("home"));

    // Items category
    public static final Section SHROOM_BIT = new Section(new ItemStack(TheoraItems.SHROOM_BIT));
    public static final Section GLIOPHIN = new Section(new ItemStack(TheoraItems.GLIOPHIN));
    public static final Section WAND = new Section(new ItemStack(TheoraItems.WAND));
    public static final Section CLEAR_SLIME = new Section(new ItemStack(TheoraItems.CLEAR_SLIME));
    public static final Section SOUL_EGG = new Section(new ItemStack(TheoraItems.SOUL_EGG));
    public static final Section VIAL = new Section(new ItemStack(TheoraItems.VIAL));
    public static final Section PIG_COIN = new Section(new ItemStack(TheoraItems.PIG_COIN));
    public static final Section PIG_COIN_BAG = new Section(new ItemStack(TheoraItems.PIG_COIN_BAG));
    public static final Section WITHER_TEAR = new Section(new ItemStack(TheoraItems.WITHER_TEAR));
    public static final Section SLATES = new Section(new ItemStack(TheoraItems.XP_SLATE));


    static {
        ITEMS.addEntry(
                new PageButtons(
                        SHROOM_BIT.addEntry(new PageItem().setText("shroom.bit0"), new PageText("shroom.bit1")).setName("shroom.bits"),
                        GLIOPHIN.addEntry(new PageItem().setText("gliophin0")),
                        WAND.addEntry(new PageItem().setText("wand0")),
                        CLEAR_SLIME.addEntry(new PageItem().setText("clear.slime0")),
                        SOUL_EGG.addEntry(new PageItem().setText("soul.egg0")),
                        VIAL.addEntry(new PageItem().setText("vial0")),
                        PIG_COIN.addEntry(new PageItem().setText("pig.coin0")),
                        PIG_COIN_BAG.addEntry(new PageItem().setText("pig.coin.bag0")),
                        WITHER_TEAR.addEntry(new PageItem().setText("wither.tear0")),
                        SLATES.addEntry(new PageItem().setText("empty.slate0")).setName("slates")
                )
        );
    }

    public static void register() {
        MAIN_SECTIONS.add(GETTING_STARTED);
        MAIN_SECTIONS.add(ITEMS);
        MAIN_SECTIONS.add(BLOCKS);
        MAIN_SECTIONS.add(LIQUIDS);
        MAIN_SECTIONS.add(ABILITIES);
        MAIN_SECTIONS.add(MISC);
    }
}
