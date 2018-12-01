package xieao.theora.common.lib.book;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.client.gui.book.GuiBook;
import xieao.theora.client.gui.button.Button;
import xieao.theora.common.lib.TheoraSounds;

import java.util.List;

public class Page {

    public String title = "";
    public Entry parentEntry = new Entry();

    public Page setTitle(String title) {
        this.title = title;
        return this;
    }

    public void setParentEntry(Entry parentEntry) {
        this.parentEntry = parentEntry;
    }

    @SideOnly(Side.CLIENT)
    public void initGui(GuiBook gui) {
        if (this.title.equals("home")) {
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 4; ++j) {
                    int index = j + i * 4;
                    List<Section> categories = TheoraBook.MAIN_SECTIONS;
                    if (index < categories.size()) {
                        Button button = new Button(index + 20, gui.x + 17 + j * 42, gui.y + 17 + i * 42).setDim(34, 34)
                                .setBg(GuiBook.BOOK_TEXTURE, gui.w, 0, false).setName(categories.get(index).name, 0)
                                .setIcon(categories.get(index).texture, 28, 28, true, 0x875a58).setSound(TheoraSounds.PAGE_FLIP);
                        gui.getButtonList().add(button);
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void actionPerformed(GuiBook gui, GuiButton button) {
        if (this.title.equals("home")) {
            Section category = TheoraBook.MAIN_SECTIONS.get(button.id - 20);
            if (category.entry != null) {
                gui.mc.displayGuiScreen(new GuiBook(category.entry, 0));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void draw(GuiBook gui, int mouseX, int mouseY, float partialTicks) {
        if (this.title.equals("home")) {

        }
    }

    protected void drawTitle(GuiBook gui, FontRenderer fr, String title, int y) {
        title = TextFormatting.BOLD + "" + TextFormatting.UNDERLINE + title;
        fr.drawString(title, (gui.w / 2) - fr.getStringWidth(title) / 2, y, 0x82756C);
    }
}
