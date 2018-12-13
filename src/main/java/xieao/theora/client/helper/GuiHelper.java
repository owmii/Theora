package xieao.theora.client.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class GuiHelper {

    public static void drawSizedTextureModalRect(int x, int y, int width, int height) {
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
    }

    public static void drawItemStack(ItemStack stack, int x, int y, String altText) {
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        Minecraft mc = Minecraft.getMinecraft();
        mc.getRenderItem().zLevel = 200.0F;
        FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) {
            font = mc.fontRenderer;
        }
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        mc.getRenderItem().renderItemOverlayIntoGUI(font, stack, x, y, altText);
        RenderHelper.disableStandardItemLighting();
        mc.getRenderItem().zLevel = 0.0F;
    }
}
