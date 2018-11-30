package xieao.theora.common.item;

import org.apache.commons.lang3.tuple.Pair;
import xieao.theora.common.lib.book.BookEntry;

import javax.annotation.Nullable;

public interface IBookItem {

    @Nullable
    Pair<BookEntry, Integer> getBookPage();

    <T extends ItemBase & IBookItem> T setBookPage(BookEntry entry, int index);

}
