package xieao.theora.client.helper;

import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class ColorHelper {

    public static void glColor(Color color) {
        glColor(color, 1.0F);
    }

    public static void glColor(Color color, float alpha) {
        float[] rbg = getColorRBG(color);
        GlStateManager.color(rbg[0], rbg[1], rbg[2], alpha);
    }

    public static void glColor(int color) {
        glColor(color, 1.0F);
    }

    public static void glColor(int color, float alpha) {
        float[] rbg = getColorRBG(color);
        GlStateManager.color(rbg[0], rbg[1], rbg[2], alpha);
    }

    public static float[] getColorRBG(int color) {
        return getColorRBG(new Color(color));
    }

    public static float[] getColorRBG(Color color) {
        float red = color.getRed() / 255.0F;
        float green = color.getGreen() / 255.0F;
        float blue = color.getBlue() / 255.0F;
        return new float[]{red, green, blue};
    }

    public static int blend(int c1, int c2, float ratio) {
        if (ratio > 1f) ratio = 1f;
        else if (ratio < 0f) ratio = 0f;
        float iRatio = 1.0f - ratio;
        int a1 = c1 >> 24 & 0xff;
        int r1 = (c1 & 0xff0000) >> 16;
        int g1 = (c1 & 0xff00) >> 8;
        int b1 = c1 & 0xff;
        int a2 = c2 >> 24 & 0xff;
        int r2 = (c2 & 0xff0000) >> 16;
        int g2 = (c2 & 0xff00) >> 8;
        int b2 = c2 & 0xff;
        int a = (int) ((a1 * iRatio) + (a2 * ratio));
        int r = (int) ((r1 * iRatio) + (r2 * ratio));
        int g = (int) ((g1 * iRatio) + (g2 * ratio));
        int b = (int) ((b1 * iRatio) + (b2 * ratio));
        return a << 24 | r << 16 | g << 8 | b;
    }

}
