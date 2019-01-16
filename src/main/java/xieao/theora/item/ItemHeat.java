package xieao.theora.item;

import net.minecraft.item.ItemStack;

public class ItemHeat extends IItem.Block {
    public ItemHeat(net.minecraft.block.Block block, Builder builder) {
        super(block, builder);
    }

    public int getAge(ItemStack stack) {
        return stack.getOrCreateTag().getInt("HeatAge");
    }

    public void setAge(ItemStack stack, int age) {
        stack.getOrCreateTag().setInt("HeatAge", age);
    }
}
