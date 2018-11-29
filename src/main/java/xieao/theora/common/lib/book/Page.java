package xieao.theora.common.lib.book;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.client.gui.book.GuiBook;

public class Page {

    public String title = "";
    public BookEntry parentEntry = new BookEntry();

    public Page setTitle(String title) {
        this.title = title;
        return this;
    }

    public void setParentEntry(BookEntry parentEntry) {
        this.parentEntry = parentEntry;
    }

    @SideOnly(Side.CLIENT)
    public void draw(GuiBook gui, int mouseX, int mouseY, float partialTicks) {

    }
}
