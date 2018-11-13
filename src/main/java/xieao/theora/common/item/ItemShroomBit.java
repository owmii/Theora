package xieao.theora.common.item;

import xieao.theora.common.block.misc.BlockShroom;

public class ItemShroomBit extends ItemBase {

    public ItemShroomBit() {
        setHasSubtypes(true);
    }

    @Override
    public Enum<?>[] getSubTypeValues() {
        return BlockShroom.Type.values();
    }
}
