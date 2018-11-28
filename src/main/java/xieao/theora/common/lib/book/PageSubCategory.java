package xieao.theora.common.lib.book;

public class PageSubCategory extends Page {

    public BookCategory[] subCategories;

    public PageSubCategory(BookCategory... subCategories) {
        this.subCategories = subCategories;
    }
}
