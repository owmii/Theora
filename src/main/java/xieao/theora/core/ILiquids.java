package xieao.theora.core;

import xieao.theora.api.liquid.Liquid;
import xieao.theora.core.lib.annotation.Initialize;

@Initialize
public class ILiquids {
    public static final Liquid ESSENCE;
    public static final Liquid FORALIS;
    public static final Liquid VEA;
    public static final Liquid VERVY;
    public static final Liquid DEAPEN;
    public static final Liquid ZIMONA;

    static {
        ESSENCE = Liquid.register("essence", 0xF197FF, 0xF7C4FF);
        FORALIS = Liquid.register("limy", 0xA6FF00, 0xD7FF00);
        VEA = Liquid.register("vea", 0xFFA48E, 0xFFBC8E);
        ZIMONA = Liquid.register("zimona", 0xFFA48E, 0xFFBC8E);
        DEAPEN = Liquid.register("deapen", 0xFFA48E, 0xFFBC8E);
        VERVY = Liquid.register("vervy", 0xFFA48E, 0xFFBC8E);
    }
}
