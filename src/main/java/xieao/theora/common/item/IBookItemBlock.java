package xieao.theora.common.item;

import org.apache.commons.lang3.tuple.Pair;
import xieao.theora.common.lib.book.BookEntry;

import javax.annotation.Nullable;

public interface IBookItemBlock {

    @Nullable
    Pair<BookEntry, Integer> getBookPage();

    <T extends ItemBlockBase & IBookItemBlock> T setBookPage(BookEntry entry, int index);

}
