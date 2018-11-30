package xieao.theora.common.lib.book;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import xieao.theora.client.gui.book.GuiBook;
import xieao.theora.client.gui.button.Button;

public class PageSubCategory extends Page {

    public BookCategory[] subCategories;

    public PageSubCategory(BookCategory... subCategories) {
        this.subCategories = subCategories;
    }

    @Override
    public void initGui(GuiBook gui) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 6; ++j) {
                int index = j + i * 6;
                if (index < this.subCategories.length) {
                    BookCategory category = this.subCategories[index];
                    ItemStack stack = category.stack;
                    Button button = new Button(index + 20, gui.x + 13 + j * 29, gui.y + 13 + i * 29).setIcon(stack, 1.25f, true)
                            .setName(category.name.isEmpty() ? category.stack.getDisplayName() : category.name, 0).setDim(24, 24)
                            .setBg(GuiBook.BOOK_TEXTURE, gui.w, 34, false);
                    gui.getButtonList().add(button);
                }
            }
        }
    }

    @Override
    public void actionPerformed(GuiBook gui, GuiButton button) {
        BookCategory category = this.subCategories[button.id - 20];
        gui.mc.displayGuiScreen(new GuiBook(category.entry, 0));
    }

    @Override
    public void draw(GuiBook gui, int mouseX, int mouseY, float partialTicks) {

    }
}
