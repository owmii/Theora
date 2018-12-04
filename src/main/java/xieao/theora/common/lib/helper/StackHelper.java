package xieao.theora.common.lib.helper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class StackHelper {

    public static NonNullList<ItemStack> getSubItems(ItemStack stack, CreativeTabs tab) {
        NonNullList<ItemStack> subItems = NonNullList.create();
        stack.getItem().getSubItems(tab, subItems);
        return subItems;
    }

    public static NonNullList<ItemStack> getSubItems(Item item, CreativeTabs tab) {
        NonNullList<ItemStack> subItems = NonNullList.create();
        item.getSubItems(tab, subItems);
        return subItems;
    }
}
