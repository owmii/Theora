package xieao.theora.core;

import xieao.theora.api.liquid.Liquid;
import xieao.theora.core.lib.annotation.AutoLoad;

@AutoLoad
public class ILiquids {
    public static final Liquid WATER;
    public static final Liquid LAVA;
    public static final Liquid MILK;

    static {
        WATER = Liquid.register("water", 0x3165E8, 0x3165E8);
        LAVA = Liquid.register("lava", 0xE8700A, 0xE8700A);
        MILK = Liquid.register("milk", 0xffffff, 0xffffff);
    }
}
