package xieao.theora.common.lib.book;

import net.minecraft.util.ResourceLocation;
import xieao.theora.Theora;

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

    public PageImage down() {
        this.pos = 1;
        return this;
    }
}
