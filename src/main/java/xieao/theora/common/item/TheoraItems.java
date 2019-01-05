package xieao.theora.common.item;

import net.minecraft.item.Item;
import xieao.lib.item.IGenericItem;
import xieao.lib.item.ItemBase;
import xieao.theora.Theora;
import xieao.theora.common.item.slates.*;

import java.util.ArrayList;
import java.util.List;

public class TheoraItems {

    public static final List<Item> ITEMS = new ArrayList<>();

    public static final Item BOOK = register(new ItemBook(), "book");
    public static final Item SHROOM_BIT = register(new ItemShroomBit(), "shroombit");
    public static final Item GLIOPHIN_STICK = register(new ItemBase(), "gliophinstick");
    public static final Item GLIOPHIN = register(new ItemGliophin(), "gliophin");
    public static final Item WAND = register(new ItemWand(), "wand");
    public static final Item LUMP = register(new ItemLump(), "lump");
    public static final Item VIAL = register(new ItemVial(), "vial");
    public static final Item PIG_COIN = register(new ItemPigCoin(), "pigcoin");
    public static final Item PIG_COIN_BAG = register(new ItemPigCoinBag(), "pigcoinbag");
    public static final Item WITHER_TEAR = register(new ItemWitherTear(), "withertear");
    public static final Item SOUL_EGG = register(new ItemSoulEgg(), "soulegg");
    public static final Item EMPTY_SLATE = register(new ItemBase(), "emptyslate");
    public static final Item SUMMONING_SLATE = register(new ItemSlateSummoning(), "summoningslate");
    public static final Item LOOTING_SLATE = register(new ItemSlateLooting(), "lootingslate");
    public static final Item EFFICIENCY_SLATE = register(new ItemSlateEfficiency(), "efficiencyslate");
    public static final Item EQUIPMENT_DROP_SLATE = register(new ItemSlateEquipmentDrop(), "equipmentdropslate");
    public static final Item XP_SLATE = register(new ItemSlateXP(), "xpslate");

    public static <T extends Item & IGenericItem> T register(T item, String name) {
        item.setRegistryName(name);
        item.setUnlocalizedName(name);
        item.setCreativeTab(Theora.TAB);
        ITEMS.add(item);
        return item;
    }
}
