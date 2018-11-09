package xieao.theora.api.trade.pigzombie;

import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PigZombieTradeHandler {

    public static final List<TradeEntry> TRADE_ENTRIES = new ArrayList<>();

    public static final String TAG_HAS_TRADES = "theora:hasTrades";
    public static final String TAG_TRADES_LIST = "theora:tradesList";


    public static List<ResourceLocation> getSortedTrades(EntityPigZombie pigZombie, int max, Random rand) {
        List<ResourceLocation> list = new ArrayList<>();
        NBTTagCompound nbt = pigZombie.getEntityData();
        boolean hasTrades = nbt.getBoolean(TAG_HAS_TRADES);
        if (hasTrades) {
            NBTTagList tagList = nbt.getTagList(TAG_TRADES_LIST, Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound nbt1 = tagList.getCompoundTagAt(i);
                String tradeName = nbt1.getString("tradeName");
                list.add(new ResourceLocation(tradeName));
            }
        } else {
            if (max <= 0) max = 1;
            List<TradeEntry> trade_entries = new ArrayList<>(TRADE_ENTRIES);
            NBTTagList tagList = new NBTTagList();
            boolean flag = false;
            for (int i = 0; i < max; i++) {
                NBTTagCompound nbt1 = new NBTTagCompound();
                TradeEntry randomItem = WeightedRandom.getRandomItem(rand, trade_entries);
                list.add(randomItem.trade);
                nbt1.setString("tradeName", randomItem.trade.toString());
                tagList.appendTag(nbt1);
                trade_entries.remove(randomItem);
                flag = true;
            }
            if (flag) {
                nbt.setTag(TAG_TRADES_LIST, tagList);
                nbt.setBoolean(TAG_HAS_TRADES, true);
            }
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
