package xieao.theora.common.book.page;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.lib.gui.GuiBase;
import xieao.lib.gui.button.Button;
import xieao.theora.Theora;
import xieao.theora.client.gui.book.GuiBook;
import xieao.theora.client.helper.I18nHelper;
import xieao.theora.common.TheoraSounds;
import xieao.theora.common.book.Entry;
import xieao.theora.common.book.Section;
import xieao.theora.common.book.TheoraBook;

import java.util.List;

public class Page {

    public static final ResourceLocation LOGO = Theora.location("textures/gui/book/logo.png");

    public String title = "";
    public Entry parentEntry = new Entry();

    public Page setTitle(String title) {
        this.title = I18nHelper.formatBookTitle(title);
        return this;
    }

    public void setParentEntry(Entry parentEntry) {
        this.parentEntry = parentEntry;
    }

    @SideOnly(Side.CLIENT)
    public void initGui(GuiBook gui) {
        if (TheoraBook.HOME.equals(gui.entry)) {
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 4; ++j) {
                    int index = j + i * 4;
                    List<Section> sections = TheoraBook.MAIN_SECTIONS;
                    if (index < sections.size()) {
                        Button button = new Button(index + 20, gui.x + 17 + j * 42, gui.y + 77 + i * 42).setDim(34, 34)
                                .setBg(gui.bg, gui.w, 0, false).setName(sections.get(index).name, 0)
                                .setIcon(sections.get(index).texture, 28, 28, true, 0x875a58).setSound(TheoraSounds.PAGE_FLIP);
                        gui.getButtonList().add(button);
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void actionPerformed(GuiBook gui, GuiButton button) {
        if (TheoraBook.HOME.equals(gui.entry)) {
            Section section = TheoraBook.MAIN_SECTIONS.get(button.id - 20);
            if (section.entry != null) {
                GuiBook.instance.open(section.entry, 0);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void draw(GuiBook gui, int mouseX, int mouseY, float partialTicks) {
        if (TheoraBook.HOME.equals(gui.entry)) {
            gui.mc.getTextureManager().bindTexture(LOGO);
            GuiBase.drawSizedTextureModalRect(32, 17, 132, 32);
            FontRenderer fr = gui.mc.fontRenderer;
            fr.drawString("Theora " + Theora.MOD_VERSION, (gui.w / 2) - fr.getStringWidth("Theora " + Theora.MOD_VERSION) / 2, 51, 0xbda5a5);
        }
    }

    protected void drawTitle(GuiBook gui, FontRenderer fr, String title, int y) {
        title = TextFormatting.BOLD + "" + TextFormatting.UNDERLINE + title;
        fr.drawString(title, (gui.w / 2) - fr.getStringWidth(title) / 2, y, 0x82756C);
    }
}
