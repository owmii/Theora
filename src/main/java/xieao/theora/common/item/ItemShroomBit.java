package xieao.theora.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import xieao.theora.common.block.misc.BlockShroom;

public class ItemShroomBit extends ItemBase {

    public ItemShroomBit() {
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getMetadata();
        return super.getUnlocalizedName() + "." + BlockShroom.Type
                .values()[meta].getName().replace("_", ".");
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            for (BlockShroom.Type type : BlockShroom.Type.values()) {
                items.add(new ItemStack(this, 1, type.ordinal()));
            }
        }
    }

    @Override
    public Enum<?>[] getSubTypeValues() {
        return BlockShroom.Type.values();
    }
}
