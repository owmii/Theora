package xieao.theora.item;

import net.minecraft.item.ItemStack;

public class ItemHeat extends IItem.Block {
    public ItemHeat(net.minecraft.block.Block block, Properties properties) {
        super(block, properties);
    }

    public int getAge(ItemStack stack) {
        return stack.getOrCreateTag().getInt("HeatAge");
    }

    public void setAge(ItemStack stack, int age) {
        stack.getOrCreateTag().putInt("HeatAge", age);
    }
}
