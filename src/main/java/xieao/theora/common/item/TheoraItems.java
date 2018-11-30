package xieao.theora.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import xieao.theora.Theora;
import xieao.theora.common.block.IGenericBlock;
import xieao.theora.common.block.TheoraBlocks;
import xieao.theora.common.item.slates.*;
import xieao.theora.common.lib.book.TheoraBook;

import java.util.ArrayList;
import java.util.List;

public class TheoraItems {

    public static final List<Item> ITEMS = new ArrayList<>();

    public static final ItemShroomBit SHROOM_BIT;
    public static final ItemGliophin GLIOPHIN;
    public static final ItemClearSlime CLEAR_SLIME;
    public static final ItemWand WAND;
    public static final ItemVial VIAL;
    public static final ItemPigCoin PIG_COIN;
    public static final ItemPigCoinBag PIG_COIN_BAG;
    public static final ItemWitherTear WITHER_TEAR;
    public static final ItemSoulEgg SOUL_EGG;
    public static final ItemBase EMPTY_SLATE;
    public static final ItemSlateSummoning SUMMONING_SLATE;
    public static final ItemSlateLooting LOOTING_SLATE;
    public static final ItemSlateEfficiency EFFICIENCY_SLATE;
    public static final ItemSlateEquipmentDrop EQUIPMENT_DROP_SLATE;
    public static final ItemSlateXP XP_SLATE;

    static {
        SHROOM_BIT = register(new ItemShroomBit(), "shroombit").setBookPage(TheoraBook.SUB_CAT_SHROOM_BIT.entry, 0);
        GLIOPHIN = register(new ItemGliophin(), "gliophin").setBookPage(TheoraBook.SUB_CAT_GLIOPHIN.entry, 0);
        CLEAR_SLIME = register(new ItemClearSlime(), "clearslime");
        WAND = register(new ItemWand(), "wand").setBookPage(TheoraBook.SUB_CAT_WAND.entry, 0);
        VIAL = register(new ItemVial(), "vial").setBookPage(TheoraBook.SUB_CAT_VIAL.entry, 0);
        PIG_COIN = register(new ItemPigCoin(), "pigcoin").setBookPage(TheoraBook.SUB_CAT_PIG_COIN.entry, 0);
        PIG_COIN_BAG = register(new ItemPigCoinBag(), "pigcoinbag").setBookPage(TheoraBook.SUB_CAT_PIG_COIN_BAG.entry, 0);
        WITHER_TEAR = register(new ItemWitherTear(), "withertear").setBookPage(TheoraBook.SUB_CAT_WITHER_TEAR.entry, 0);
        SOUL_EGG = register(new ItemSoulEgg(), "soulegg").setBookPage(TheoraBook.SUB_CAT_SOUL_EGG.entry, 0);
        EMPTY_SLATE = register(new ItemBase(), "emptyslate").setBookPage(TheoraBook.SUB_CAT_SLATES.entry, 0);
        SUMMONING_SLATE = register(new ItemSlateSummoning(), "summoningslate").setBookPage(TheoraBook.SUB_CAT_SLATES.entry, 0);
        LOOTING_SLATE = register(new ItemSlateLooting(), "lootingslate").setBookPage(TheoraBook.SUB_CAT_SLATES.entry, 0);
        EFFICIENCY_SLATE = register(new ItemSlateEfficiency(), "efficiencyslate").setBookPage(TheoraBook.SUB_CAT_SLATES.entry, 0);
        EQUIPMENT_DROP_SLATE = register(new ItemSlateEquipmentDrop(), "equipmentdropslate").setBookPage(TheoraBook.SUB_CAT_SLATES.entry, 0);
        XP_SLATE = register(new ItemSlateXP(), "xpslate").setBookPage(TheoraBook.SUB_CAT_SLATES.entry, 0);

        for (Block block : TheoraBlocks.BLOCKS) {
            if (block instanceof IGenericBlock) {
                IGenericBlock block1 = (IGenericBlock) block;
                ResourceLocation location = block.getRegistryName();
                register(block1.getItemBlock(), location.toString());
            }
        }
    }

    private static <T extends Item & IGenericItem> T register(T item, String name) {
        item.setRegistryName(name);
        item.setUnlocalizedName(name);
        item.setCreativeTab(Theora.TAB);
        ITEMS.add(item);
        return item;
    }
}
