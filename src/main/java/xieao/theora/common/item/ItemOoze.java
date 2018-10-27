package xieao.theora.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import xieao.theora.common.block.misc.BlockShroom;

public class ItemOoze extends ItemBase {

    public ItemOoze() {
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getMetadata();
        BlockShroom.Type type = BlockShroom.Type.values()[meta];
        String name = type.getName().replace("_", ".");
        return super.getUnlocalizedName() + "." + name;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            for (BlockShroom.Type type : BlockShroom.Type.values()) {
                items.add(new ItemStack(this, 1, type.ordinal()));
            }
        }
    }
}
