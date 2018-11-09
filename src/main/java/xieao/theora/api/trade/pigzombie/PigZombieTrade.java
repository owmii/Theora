package xieao.theora.api.trade.pigzombie;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xieao.theora.api.registry.RegistryEntry;

import java.util.HashMap;
import java.util.Map;

public class PigZombieTrade extends RegistryEntry<PigZombieTrade> {

    public static final Map<ResourceLocation, PigZombieTrade> REGISTRY;
    public static final PigZombieTrade EMPTY = new PigZombieTrade(ItemStack.EMPTY, 0, TradRarity.NORMAL);

    public final ItemStack itemToSell;
    public final int price;
    public final TradRarity rarity;

    static {
        REGISTRY = new HashMap<>();
        register(EMPTY, "theora:empty");
    }

    public PigZombieTrade(ItemStack itemToSell, int price, TradRarity rarity) {
        this.itemToSell = itemToSell;
        this.price = price;
        this.rarity = rarity;
    }

    public static PigZombieTrade register(PigZombieTrade pigZombieTrade, String name) {
        pigZombieTrade.setRegistryName(name);
        REGISTRY.put(pigZombieTrade.getRegistryName(), pigZombieTrade);
        return pigZombieTrade;
    }

    public static PigZombieTrade getPigZombieTrade(String name) {
        PigZombieTrade pigZombieTrade = REGISTRY.get(new ResourceLocation(name));
        return pigZombieTrade == null ? EMPTY : pigZombieTrade;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public enum TradRarity {
        VERY_RARE, RARE, NORMAL
    }
}
