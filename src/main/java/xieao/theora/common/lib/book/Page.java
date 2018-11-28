package xieao.theora.common.lib.book;

public class Page {

    public String title = "";
    public BookEntry parentEntry = new BookEntry();

    public void setTitle(String title) {
        this.title = title;
    }

    public void setParentEntry(BookEntry parentEntry) {
        this.parentEntry = parentEntry;
    }
}
