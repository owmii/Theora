package xieao.theora.common.lib.book;

public class BookEntry {

    public Page[] bookPages = new Page[0];
    public BookCategory parentCategoty = new BookCategory("null");

    public void setBookPages(Page... bookPages) {
        for (Page page : bookPages) {
            page.setParentEntry(this);
        }
        this.bookPages = bookPages;
    }

    public Page getBookPage(int index) {
        return bookPages[index];
    }

    public void setParentCategoty(BookCategory parentCategoty) {
        this.parentCategoty = parentCategoty;
    }
}
