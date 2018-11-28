package xieao.theora.common.lib.book;

import net.minecraft.item.ItemStack;

public class PageItem extends PageText {

    public ItemStack stack;

    public PageItem() {
        super("");
        this.stack = this.parentEntry.parentCategoty.stack;
    }
}
