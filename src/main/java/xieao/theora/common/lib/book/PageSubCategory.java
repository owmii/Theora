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
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 4; ++j) {
                int index = j + i * 4;
                if (index < this.subCategories.length) {
                    BookCategory category = this.subCategories[index];
                    ItemStack stack = category.stack;
                    Button button = new Button(index + 20, gui.x + 12 + j * 30, gui.y + 12 + i * 30).setIcon(stack, 1.25f, true)
                            .setName(category.name.isEmpty() ? category.stack.getDisplayName() : category.name, 0).setDim(26, 26)
                            .setBg(GuiBook.BOOK_TEXTURE, gui.w / 2, 0, false);
                    gui.getButtonList().add(button);
                }
            }
        }
    }

    @Override
    public void actionPerformed(GuiBook gui, GuiButton button) {

    }

    @Override
    public void draw(GuiBook gui, int mouseX, int mouseY, float partialTicks) {

    }
}
