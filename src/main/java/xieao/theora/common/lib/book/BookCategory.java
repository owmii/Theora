package xieao.theora.common.lib.book;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xieao.theora.Theora;

public class BookCategory {

    public String name = "";
    public ResourceLocation texture;
    public BookEntry entry = null;
    public ItemStack stack = ItemStack.EMPTY;


    public BookCategory(ItemStack stack) {
        this.texture = Theora.location("null");
        this.stack = stack;
        this.name = "";
    }

    public BookCategory(String name, ItemStack stack) {
        this.texture = Theora.location("null");
        setName(name);
        this.stack = stack;
    }

    public BookCategory(String name) {
        setName(name);
        this.texture = Theora.location("textures/gui/book/categories/" + name + ".png");
    }

    public BookCategory addEntry(Page... pages) {
        this.entry = new BookEntry();
        this.entry.setBookPages(pages);
        this.entry.setParentCategoty(this);
        return this;
    }

    public BookCategory setName(String name) {
        this.name = I18n.format("theora.book.cat." + name);
        return this;
    }
}
