package xieao.theora.common.item.slates;

import net.minecraft.item.ItemStack;
import xieao.theora.api.item.slate.IEfficiencySlate;
import xieao.theora.common.item.ItemBase;

public class ItemSlateEfficiency extends ItemBase implements IEfficiencySlate {

    @Override
    public float getLiquidCost(ItemStack stack) {
        return Level.values()[stack.getMetadata()].liquidCost;
    }

    @Override
    public int getDelayTicks(ItemStack stack) {
        return Level.values()[stack.getMetadata()].delayTicks;
    }

    @Override
    public Enum<?>[] getSubTypeValues() {
        return Level.values();
    }

    public enum Level {
        LEVEL_1(80, 20.0F),
        LEVEL_2(60, 25.0F),
        LEVEL_3(40, 30.0F),
        LEVEL_4(20, 35.0F),
        LEVEL_5(10, 45.0F);

        public final int delayTicks;
        public final float liquidCost;

        Level(int delayTicks, float liquidCost) {
            this.delayTicks = delayTicks;
            this.liquidCost = liquidCost;
        }
    }
}
