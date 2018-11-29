package xieao.theora.common.lib.book;

import net.minecraft.util.ResourceLocation;
import xieao.theora.Theora;
import xieao.theora.client.gui.book.GuiBook;

public class PageImage extends PageText {

    public ResourceLocation texture;
    public int w;
    public int h;
    public int pos;

    public PageImage(String texture, int w, int h) {
        super("");
        this.texture = Theora.location("textures/gui/book/data/" + texture + ".png");
        this.w = w;
        this.h = h;
    }

    @Override
    public void draw(GuiBook gui, int mouseX, int mouseY, float partialTicks) {
        gui.mc.getTextureManager().bindTexture(this.texture);
        gui.drawTexturedModalRect(0, 0, 0, 0, this.w, this.h);
        super.draw(gui, mouseX, mouseY, partialTicks);
    }

    public PageImage down() {
        this.pos = 1;
        return this;
    }
}
