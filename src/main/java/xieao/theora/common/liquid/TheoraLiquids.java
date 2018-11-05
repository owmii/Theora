package xieao.theora.common.liquid;

import xieao.theora.api.liquid.Liquid;

public class TheoraLiquids {

    public static final Liquid LEQUEN = new Liquid();

    public static void register() {
        Liquid.register(LEQUEN, "lequen", 0xffe0e7, 0xfff7f4);
    }
}
