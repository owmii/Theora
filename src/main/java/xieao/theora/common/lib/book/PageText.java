package xieao.theora.common.lib.book;

import net.minecraft.client.resources.I18n;

public class PageText extends Page {

    public String text;

    public PageText(String text) {
        this.text = I18n.format("theora.book.text." + text);
    }

    public PageText setText(String text) {
        this.text = text;
        return this;
    }
}
