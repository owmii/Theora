package xieao.theora.client.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;

public class GuiHelper {

    public static void drawTexturedModalRect(int x, int y, int width, int height, float zLevel) {
        float f0 = 1.0F / (float) width;
        float f1 = 1.0F / (float) height;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double) (x), (double) (y + height), (double) zLevel).tex((double) (f0), (double) ((float) (height) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + height), (double) zLevel).tex((double) ((float) (width) * f0), (double) ((float) (height) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y), (double) zLevel).tex((double) ((float) (width) * f0), (double) ((float) f1)).endVertex();
        bufferbuilder.pos((double) (x), (double) (y), (double) zLevel).tex((double) (f0), (double) ((float) f1)).endVertex();
        tessellator.draw();
    }

    public static void drawItemStack(ItemStack stack, int x, int y, String altText) {
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        Minecraft mc = Minecraft.getMinecraft();
        mc.getRenderItem().zLevel = 200.0F;
        FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = mc.fontRenderer;
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        mc.getRenderItem().renderItemOverlayIntoGUI(font, stack, x, y, altText);
        mc.getRenderItem().zLevel = 0.0F;
    }
}
