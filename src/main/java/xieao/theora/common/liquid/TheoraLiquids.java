package xieao.theora.common.liquid;

import xieao.theora.api.liquid.Liquid;

import java.awt.*;

public class TheoraLiquids {

    public static final Liquid LAOWM = new Liquid();
    public static final Liquid GLIOPHIN = new Liquid();
    public static final Liquid VEANTA = new Liquid();
    public static final Liquid VERVY = new Liquid();

    public static final Liquid DEAPEN = new Liquid();
    public static final Liquid FORALIS = new Liquid();
    public static final Liquid ZIMONA = new Liquid();
    public static final Liquid SQUINK = new Liquid();
    public static final Liquid LEQUEN = new Liquid();

    public static void register() {
        Liquid.register(LAOWM, "laowm", 0xcfe4e9, 0xffffff);
        Liquid.register(GLIOPHIN, "gliophin", 0x85be00, 0xd1ff00);
        Liquid.register(VEANTA, "veanta", 0xcf2e1a, 0xe27653);
        Liquid.register(VERVY, "vervy", 0x1e81ce, 0x23ace3);

//        Liquid.register(DEAPEN, "deapen", 0x889a9c, 0xffffff);
//        Liquid.register(FORALIS, "foralis", 0xf7279e, 0xffffff);
//        Liquid.register(ZIMONA, "zimona", 0x905fab, 0xffffff);
//        Liquid.register(SQUINK, "squink", 0x380d5c, 0xffffff);
        Liquid.register(LEQUEN, "lequen", 0xffe0e7, 0xfff7f4);
        new Color(0x23ace3);
    }
}
