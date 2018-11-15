package xieao.theora.common.trade;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xieao.theora.api.trade.pigzombie.PigZombieTrade;
import xieao.theora.api.trade.pigzombie.PigZombieTradeHandler;

import java.util.Map;

public class PigZomieTrades {

    public static void register() {
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.NETHERBRICK, 16), 2, PigZombieTrade.TradRarity.NORMAL), "state.netherbrick");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Blocks.NETHER_BRICK, 16), 8, PigZombieTrade.TradRarity.NORMAL), "state.netherbricks");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Blocks.GLOWSTONE, 4), 5, PigZombieTrade.TradRarity.RARE), "state.glowstone");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.GLOWSTONE_DUST, 16), 3, PigZombieTrade.TradRarity.NORMAL), "state.glowstonedust");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Blocks.GOLD_BLOCK), 18, PigZombieTrade.TradRarity.VERY_RARE), "state.goldblock");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.GOLD_INGOT, 5), 10, PigZombieTrade.TradRarity.RARE), "state.goldingot");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.GOLD_NUGGET, 18), 4, PigZombieTrade.TradRarity.NORMAL), "state.goldnugget");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.CARROT, 3), 1, PigZombieTrade.TradRarity.RARE), "state.carrot");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.GOLDEN_CARROT, 8), 5, PigZombieTrade.TradRarity.VERY_RARE), "state.goldencarrot");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.GOLDEN_APPLE, 2), 24, PigZombieTrade.TradRarity.RARE), "state.goldenapple");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.GOLDEN_APPLE, 1), 124, PigZombieTrade.TradRarity.VERY_RARE), "state.goldenapple2");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.GOLDEN_AXE), 7, PigZombieTrade.TradRarity.RARE), "state.goldenaxe");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.GOLDEN_PICKAXE), 7, PigZombieTrade.TradRarity.RARE), "state.goldenpickaxe");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.GOLDEN_SHOVEL), 4, PigZombieTrade.TradRarity.RARE), "state.goldenshovel");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.GOLDEN_SWORD), 5, PigZombieTrade.TradRarity.RARE), "state.goldensword");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.GOLDEN_HOE), 5, PigZombieTrade.TradRarity.RARE), "state.goldenhoe");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.GOLDEN_HELMET), 12, PigZombieTrade.TradRarity.RARE), "state.goldenhelmet");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.GOLDEN_CHESTPLATE), 18, PigZombieTrade.TradRarity.RARE), "state.goldenchestplate");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.GOLDEN_LEGGINGS), 15, PigZombieTrade.TradRarity.RARE), "state.goldenleggings");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.GOLDEN_BOOTS), 6, PigZombieTrade.TradRarity.RARE), "state.goldenboots");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.GOLDEN_HORSE_ARMOR), 19, PigZombieTrade.TradRarity.VERY_RARE), "state.goldenhorsarmor");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.NETHER_WART, 4), 9, PigZombieTrade.TradRarity.RARE), "state.netherwart");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.NAME_TAG), 29, PigZombieTrade.TradRarity.VERY_RARE), "state.nametag");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.ENCHANTED_BOOK), 30, PigZombieTrade.TradRarity.VERY_RARE), "state.enchantedbook");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Items.FLINT_AND_STEEL), 3, PigZombieTrade.TradRarity.RARE), "state.flintandsteel");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Blocks.BROWN_MUSHROOM, 4), 2, PigZombieTrade.TradRarity.NORMAL), "state.brownmushroom");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Blocks.RED_MUSHROOM, 4), 2, PigZombieTrade.TradRarity.NORMAL), "state.redmushroom");
        PigZombieTrade.register(new PigZombieTrade(new ItemStack(Blocks.SOUL_SAND, 16), 5, PigZombieTrade.TradRarity.NORMAL), "state.redmushroom");
    }

    public static void postInit() {
        for (Map.Entry<ResourceLocation, PigZombieTrade> entry : PigZombieTrade.REGISTRY.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                PigZombieTradeHandler.TRADE_ENTRIES.add(new PigZombieTradeHandler.TradeEntry(entry.getKey(),
                        entry.getValue().rarity.ordinal() + 1));
            }
        }
    }

}
