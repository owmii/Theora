package xieao.theora.common.lib.book;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.client.gui.book.GuiBook;

public class PageText extends Page {

    public String text;

    public PageText(String text) {
        this.text = I18n.format("theora.book.text." + text);
    }

    public PageText setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(GuiBook gui, int mouseX, int mouseY, float partialTicks) {

    }
}
