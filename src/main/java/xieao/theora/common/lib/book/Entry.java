package xieao.theora.common.lib.book;

public class Entry {

    public Page[] bookPages = new Page[0];
    public Section parentCategoty = new Section("null");

    public Entry setBookPages(Page... bookPages) {
        for (Page page : bookPages) {
            page.setParentEntry(this);
        }
        this.bookPages = bookPages;
        return this;
    }

    public Page getBookPage(int index) {
        return bookPages[index];
    }

    public void setParentCategoty(Section parentCategoty) {
        this.parentCategoty = parentCategoty;
    }
}
