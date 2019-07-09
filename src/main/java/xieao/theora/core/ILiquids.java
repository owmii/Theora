package xieao.theora.core;

import xieao.theora.api.liquid.Liquid;
import xieao.theora.core.lib.annotation.Initialize;

@Initialize
public class ILiquids {
    public static final Liquid GLIOPHIN;

    static {
        GLIOPHIN = Liquid.register("gliophin", 0x85be00, 0xd1ff00);
    }
}
