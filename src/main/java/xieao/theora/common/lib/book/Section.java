package xieao.theora.common.lib.book;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xieao.theora.Theora;
import xieao.theora.client.helper.I18nHelper;
import xieao.theora.common.lib.book.page.Page;

import javax.annotation.Nullable;

public class Section {

    public String name = "";
    public ResourceLocation texture;
    public ItemStack stack = ItemStack.EMPTY;

    @Nullable
    public Entry entry;


    public Section(ItemStack stack) {
        this.texture = Theora.location("null");
        this.stack = stack;
        this.name = "";
    }

    public Section(String name, ItemStack stack) {
        this.texture = Theora.location("null");
        setName(name);
        this.stack = stack;
    }

    public Section(String name) {
        setName(name);
        this.texture = Theora.location("textures/gui/book/categories/" + name + ".png");
    }

    public Section addEntry(Page... pages) {
        this.entry = new Entry();
        this.entry.setPages(pages);
        this.entry.setParentSection(this);
        return this;
    }

    public Section setName(String name) {
        this.name = I18nHelper.formatBookSection(name);
        return this;
    }

    public Section setStack(ItemStack stack) {
        this.stack = stack;
        return this;
    }
}
