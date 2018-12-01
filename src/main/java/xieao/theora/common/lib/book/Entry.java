package xieao.theora.common.lib.book;

import xieao.theora.common.lib.book.page.Page;

public class Entry {

    public Page[] pages = new Page[0];
    public Section parentSection = new Section("null");

    public Entry setPages(Page... pages) {
        for (Page page : pages) {
            page.setParentEntry(this);
        }
        this.pages = pages;
        return this;
    }

    public Page getPage(int index) {
        return pages[index];
    }

    public void setParentSection(Section section) {
        this.parentSection = section;
    }
}
