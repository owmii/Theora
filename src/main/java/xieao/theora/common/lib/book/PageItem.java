package xieao.theora.common.lib.book;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import xieao.theora.client.gui.book.GuiBook;
import xieao.theora.client.helper.GuiHelper;

public class PageItem extends PageText {

    public ItemStack stack;

    public PageItem() {
        super("");
        this.stack = this.parentEntry.parentCategoty.stack;
    }

    @Override
    public void draw(GuiBook gui, int mouseX, int mouseY, float partialTicks) {
        GlStateManager.scale(2.0F, 2.0F, 0.0F);
        GuiHelper.drawItemStack(this.parentEntry.parentCategoty.stack, 0, 0, "");
    }

    public PageItem setStack(ItemStack stack) {
        this.stack = stack;
        return this;
    }
}
