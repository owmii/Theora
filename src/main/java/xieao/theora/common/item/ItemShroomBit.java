package xieao.theora.common.item;

import xieao.lib.item.ItemBase;
import xieao.theora.common.block.misc.BlockShroom;

public class ItemShroomBit extends ItemBase {

    public ItemShroomBit() {
        setHasSubtypes(true);
    }

    @Override
    public Enum<?>[] getSubTypes() {
        return BlockShroom.Type.values();
    }
}
