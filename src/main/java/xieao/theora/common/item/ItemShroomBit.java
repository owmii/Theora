package xieao.theora.common.item;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xieao.theora.common.block.misc.BlockShroom;
import xieao.theora.common.lib.book.Entry;
import xieao.theora.common.lib.book.TheoraBook;

import javax.annotation.Nullable;

public class ItemShroomBit extends ItemBase {

    public ItemShroomBit() {
        setHasSubtypes(true);
    }

    @Override
    public Enum<?>[] getSubTypeValues() {
        return BlockShroom.Type.values();
    }

    @Nullable
    @Override
    public Pair<Entry, Integer> getBookPage() {
        return new ImmutablePair<>(TheoraBook.SHROOM_BIT.entry, 0);
    }
}
