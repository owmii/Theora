package xieao.theora.common.lib.book.page;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import xieao.theora.client.gui.book.GuiBook;
import xieao.theora.client.gui.button.Button;
import xieao.theora.common.lib.TheoraSounds;
import xieao.theora.common.lib.book.Page;
import xieao.theora.common.lib.book.Section;

public class PageButtons extends Page {

    public Section[] subCategories;

    public PageButtons(Section... subCategories) {
        this.subCategories = subCategories;
    }

    @Override
    public void initGui(GuiBook gui) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 6; ++j) {
                int index = j + i * 6;
                if (index < this.subCategories.length) {
                    Section category = this.subCategories[index];
                    ItemStack stack = category.stack;
                    Button button = new Button(index + 20, gui.x + 13 + j * 29, gui.y + 13 + i * 29).setIcon(stack, 1.25f, true)
                            .setName(category.name.isEmpty() ? category.stack.getDisplayName() : category.name, 0).setDim(24, 24)
                            .setBg(GuiBook.BOOK_TEXTURE, gui.w, 34, false).setSound(TheoraSounds.PAGE_FLIP);
                    gui.getButtonList().add(button);
                }
            }
        }
    }

    @Override
    public void actionPerformed(GuiBook gui, GuiButton button) {
        Section category = this.subCategories[button.id - 20];
        if (category.entry != null && category.entry.bookPages.length > 0) {
            gui.mc.displayGuiScreen(new GuiBook(category.entry, 0));
        }
    }

    @Override
    public void draw(GuiBook gui, int mouseX, int mouseY, float partialTicks) {

    }
}
