package xieao.theora.common.lib.book.page;

import net.minecraft.client.gui.FontRenderer;
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

    public PageItem(ItemStack stack) {
        super("");
        this.stack = stack;
    }

    @Override
    public void draw(GuiBook gui, int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((gui.w / 2.0D) - 16, 12, 0);
        GlStateManager.scale(2.0F, 2.0F, 0.0F);
        GuiHelper.drawItemStack(this.parentEntry.parentCategoty.stack, 0, 0, "");
        GlStateManager.popMatrix();
        FontRenderer fr = gui.mc.fontRenderer;
        drawTitle(gui, fr, this.parentEntry.parentCategoty.name, 47);
        drawText(gui, fr, this.text, 68);
    }

    public PageItem setStack(ItemStack stack) {
        this.stack = stack;
        return this;
    }
}
