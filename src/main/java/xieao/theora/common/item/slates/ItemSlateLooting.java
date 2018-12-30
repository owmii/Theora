package xieao.theora.common.item.slates;

import net.minecraft.item.ItemStack;
import xieao.lib.item.ItemBase;
import xieao.theora.api.item.slate.ILootingSlate;

public class ItemSlateLooting extends ItemBase implements ILootingSlate {

    @Override
    public Enum<?>[] getSubTypes() {
        return Level.values();
    }

    @Override
    public int getFortuneLevel(ItemStack stack) {
        return Level.values()[stack.getMetadata()].ordinal() + 1;
    }

    @Override
    public float getLiquidCost(ItemStack stack) {
        return (Level.values()[stack.getMetadata()].ordinal() + 1) * 8.0F;
    }

    public enum Level {
        LEVEL_1, LEVEL_2, LEVEL_3
    }
}
