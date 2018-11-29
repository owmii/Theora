package xieao.theora.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xieao.theora.client.helper.ColorHelper;
import xieao.theora.client.helper.GuiHelper;

import javax.annotation.Nullable;

public class Button extends GuiButton {

    @Nullable
    private ResourceLocation bg;
    private int bgX;
    private int bgY;
    private boolean bgHover;

    @Nullable
    private ResourceLocation icon;
    private ItemStack stack = ItemStack.EMPTY;
    private int iconW;
    private int iconH;
    private int iconColor;
    private boolean iconOnly;

    private int textColor;

    public Button(int id, int x, int y) {
        super(id, x, y, "");
        setDim(20, 20);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            FontRenderer fontrenderer = mc.fontRenderer;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            if (this.bg != null) {
                mc.getTextureManager().bindTexture(this.bg);
                drawTexturedModalRect(this.x, this.y, this.bgX, this.bgY, this.width, this.height);
            }
            if (this.icon != null) {
                mc.getTextureManager().bindTexture(this.icon);
                ColorHelper.glColor(this.iconColor);
                GuiHelper.drawTexturedModalRect(this.x + (this.width - this.iconW) / 2, this.y + (this.height - this.iconH) / 2, this.iconW, this.iconH, this.zLevel);
            }
            if (!this.stack.isEmpty()) {
                GuiHelper.drawItemStack(this.stack, this.x, this.y, "");
            }
            mouseDragged(mc, mouseX, mouseY);
            if (!this.iconOnly) {
                mc.fontRenderer.drawString(this.displayString, this.x + 27, this.y + 6, this.textColor);
            }
        }
    }

    public Button setBg(@Nullable ResourceLocation bg, int bgX, int bgY, boolean bgHover) {
        this.bg = bg;
        this.bgX = bgX;
        this.bgY = bgY;
        this.bgHover = bgHover;
        return this;
    }

    public Button setIcon(ItemStack stack, boolean iconOnly) {
        this.stack = stack;
        this.iconOnly = iconOnly;
        return this;
    }

    public Button setIcon(@Nullable ResourceLocation icon, int iconW, int iconH, boolean iconOnly, int iconColor) {
        this.icon = icon;
        this.iconW = iconW;
        this.iconH = iconH;
        this.iconOnly = iconOnly;
        this.iconColor = iconColor;
        return this;
    }

    public Button setDim(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Button setName(String name, int textColor) {
        this.displayString = name;
        this.textColor = textColor;
        return this;
    }
}
