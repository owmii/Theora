package xieao.theora.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import xieao.theora.Theora;

public class ItemGroups {
    public static final ItemGroup MAIN = new ItemGroup(Theora.MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(IItems.VIAL);
        }
    };
}
