package xieao.theora.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
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
    private int iconW;
    private int iconH;
    private boolean single;
    private int iconX;
    private int iconY;
    private int iconColor;
    private ItemStack stack = ItemStack.EMPTY;
    private float scale;
    private boolean iconOnly;

    private int textColor;

    @Nullable
    private SoundEvent sound;

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
                if (this.single) {
                    GuiHelper.drawSizedTextureModalRect(this.x + (this.width - this.iconW) / 2, this.y + (this.height - this.iconH) / 2, this.iconW, this.iconH);
                } else {
                    drawTexturedModalRect(this.x + (this.width - this.iconW) / 2, this.y + (this.height - this.iconH) / 2, this.iconX, this.iconY, this.iconW, this.iconH);
                }
            }
            if (!this.stack.isEmpty()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(this.x + ((float) this.width - 16 * this.scale) / 2.0F, this.y + ((float) this.width - 16 * this.scale) / 2.0F, 0.0D);
                GlStateManager.scale(this.scale, this.scale, 0.0F);

                GuiHelper.drawItemStack(this.stack, 0, 0, "");
                GlStateManager.popMatrix();
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

    public Button setIcon(ItemStack stack, float scale, boolean iconOnly) {
        this.stack = stack;
        this.scale = scale;
        this.iconOnly = iconOnly;
        return this;
    }

    public Button setIcon(@Nullable ResourceLocation icon, int iconW, int iconH, boolean iconOnly, int iconColor) {
        this.icon = icon;
        this.iconW = iconW;
        this.iconH = iconH;
        this.iconOnly = iconOnly;
        this.iconColor = iconColor;
        this.single = true;
        return this;
    }

    public Button setIcon(@Nullable ResourceLocation icon, int iconW, int iconH, int iconX, int iconY, boolean iconOnly, int iconColor) {
        this.icon = icon;
        this.iconW = iconW;
        this.iconH = iconH;
        this.iconX = iconX;
        this.iconY = iconY;
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

    public Button setSound(@Nullable SoundEvent sound) {
        this.sound = sound;
        return this;
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
        if (this.sound != null) {
            soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(this.sound, 1.0F));
        } else {
            super.playPressSound(soundHandlerIn);
        }
    }
}
