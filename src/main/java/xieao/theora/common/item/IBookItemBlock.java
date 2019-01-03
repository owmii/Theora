package xieao.theora.common.item;

import org.apache.commons.lang3.tuple.Pair;
import xieao.lib.item.ItemBlockBase;
import xieao.theora.common.book.Entry;

import javax.annotation.Nullable;

public interface IBookItemBlock {

    @Nullable
    Pair<Entry, Integer> getBookPage();

    <T extends ItemBlockBase & IBookItemBlock> T setBookPage(Entry entry, int index);

}
