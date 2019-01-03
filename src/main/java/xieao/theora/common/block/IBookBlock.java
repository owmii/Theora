package xieao.theora.common.block;

import org.apache.commons.lang3.tuple.Pair;
import xieao.lib.item.ItemBase;
import xieao.theora.common.book.Entry;
import xieao.theora.common.item.IBookItem;

import javax.annotation.Nullable;

public interface IBookBlock {

    IBookItem getBookItem();

    @Nullable
    Pair<Entry, Integer> getBookPage();

    <T extends ItemBase & IBookItem> T setBookPage(Entry entry, int index);
}
