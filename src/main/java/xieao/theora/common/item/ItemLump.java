package xieao.theora.common.item;

import xieao.lib.item.ItemBase;
import xieao.theora.common.block.misc.BlockMineral;

public class ItemLump extends ItemBase {

    public ItemLump() {
        setHasSubtypes(true);
    }

    @Override
    public Enum<?>[] getSubTypes() {
        return BlockMineral.Type.values();
    }
}
