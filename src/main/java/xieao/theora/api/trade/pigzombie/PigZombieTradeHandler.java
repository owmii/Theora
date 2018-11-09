package xieao.theora.api.trade.pigzombie;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PigZombieTradeHandler {

    public static final List<TradeEntry> TRADE_ENTRIES = new ArrayList<>();

    public static final String TAG_HAS_TRADES = "";
    public static final String TAG_TRADES_LIST = "";


    public static List<ResourceLocation> getSortedTrades(int max, Random rand) {
        if (max <= 0) max = 1;
        List<TradeEntry> trade_entries = new ArrayList<>(TRADE_ENTRIES);
        List<ResourceLocation> list = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            TradeEntry randomItem = WeightedRandom.getRandomItem(rand, trade_entries);
            list.add(randomItem.trade);
            trade_entries.remove(randomItem);
        }
        return list;
    }

    public static class TradeEntry extends WeightedRandom.Item {

        public final ResourceLocation trade;

        public TradeEntry(ResourceLocation trade, int weight) {
            super(weight);
            this.trade = trade;
        }
    }
}
