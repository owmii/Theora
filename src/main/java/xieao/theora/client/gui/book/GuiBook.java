package xieao.theora.client.gui.book;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.Theora;
import xieao.theora.common.lib.book.BookEntry;
import xieao.theora.common.lib.book.Page;
import xieao.theora.common.lib.book.TheoraBook;

import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiBook extends GuiScreen {

    public static final ResourceLocation BOOK_TEXTURE = Theora.location("textures/gui/book/background.png");

    public int x, y, w = 196, h = 230;
    private final BookEntry bookEntry;
    private final Page page;

    public GuiBook(BookEntry bookEntry, int page) {
        this.bookEntry = bookEntry;
        this.page = bookEntry.getBookPage(page);
    }

    public GuiBook() {
        this(TheoraBook.HOME, 0);
    }

    @Override
    public void initGui() {
        this.x = (this.width - this.w) / 2;
        this.y = (this.height - this.h) / 2;
        this.page.initGui(this);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        this.page.actionPerformed(this, button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(this.x, this.y, 0.0D);
        this.mc.getTextureManager().bindTexture(BOOK_TEXTURE);
        drawTexturedModalRect(0, 0, 0, 0, this.w, this.h);
        this.page.draw(this, mouseX, mouseY, partialTicks);
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)) {
            this.mc.player.closeScreen();
        }
    }

    @Override
    public void onGuiClosed() {

    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public List<GuiButton> getButtonList() {
        return buttonList;
    }
}
