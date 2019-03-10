package xieao.theora.core;

import xieao.theora.api.liquid.Liquid;
import xieao.theora.lib.annotation.Initialize;

@Initialize
public class ILiquids {
    public static final Liquid ESSENCE;
    public static final Liquid LIMY;
    public static final Liquid VEA;

    static {
        ESSENCE = Liquid.register("essence", 0xF197FF, 0xF7C4FF);
        LIMY = Liquid.register("limy", 0xA6FF00, 0xD7FF00);
        VEA = Liquid.register("vea", 0xFFA48E, 0xFFBC8E);
    }
}
