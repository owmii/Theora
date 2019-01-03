package xieao.theora.common.book.page;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import xieao.lib.gui.button.Button;
import xieao.theora.client.gui.book.GuiBook;
import xieao.theora.common.TheoraSounds;
import xieao.theora.common.book.Section;

public class PageButtons extends Page {

    public Section[] sections;

    public PageButtons(Section... sections) {
        this.sections = sections;
    }

    @Override
    public void initGui(GuiBook gui) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 6; ++j) {
                int index = j + i * 6;
                if (index < this.sections.length) {
                    Section section = this.sections[index];
                    ItemStack stack = section.stack;
                    Button button = new Button(index + 20, gui.x + 13 + j * 29, gui.y + 13 + i * 29).setIcon(stack, 1.25f, true)
                            .setName(section.name.isEmpty() ? section.stack.getDisplayName() : section.name, 0).setDim(24, 24)
                            .setBg(gui.bg, gui.w, 34, false).setSound(TheoraSounds.PAGE_FLIP);
                    gui.getButtonList().add(button);
                }
            }
        }
    }

    @Override
    public void actionPerformed(GuiBook gui, GuiButton button) {
        Section section = this.sections[button.id - 20];
        if (section.entry != null && section.entry.pages.length > 0) {
            GuiBook.instance.open(section.entry, 0);
        }
    }

    @Override
    public void draw(GuiBook gui, int mouseX, int mouseY, float partialTicks) {

    }
}
