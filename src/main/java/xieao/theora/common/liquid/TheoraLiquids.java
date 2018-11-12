package xieao.theora.common.liquid;

import xieao.theora.api.liquid.Liquid;

import java.awt.*;

public class TheoraLiquids {

    public static final Liquid GLIOPHORIN = new Liquid();
    public static final Liquid LEQUEN = new Liquid();

    public static void register() {
        Liquid.register(GLIOPHORIN, "gliophorin", 0x85be00, 0xd1ff00);
        Liquid.register(LEQUEN, "lequen", 0xffe0e7, 0xfff7f4);
        new Color(0xd1ff00);
    }
}
