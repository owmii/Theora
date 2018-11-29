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
        for (int i = 0; i < this.subCategories.length; i++) {
            BookCategory category = this.subCategories[i];
            ItemStack stack = category.stack;
            Button button = new Button(i + 20, gui.x + 2, gui.y + 8 + i * 18).setIcon(stack, true)
                    .setName(category.name.isEmpty() ? category.stack.getDisplayName() : category.name, 0).setDim(20, 20);
            gui.getButtonList().add(button);
        }
    }

    @Override
    public void actionPerformed(GuiBook gui, GuiButton button) {
        super.actionPerformed(gui, button);
    }

    @Override
    public void draw(GuiBook gui, int mouseX, int mouseY, float partialTicks) {
        super.draw(gui, mouseX, mouseY, partialTicks);
    }
}
