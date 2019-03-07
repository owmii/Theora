package xieao.theora.core;

import xieao.theora.api.liquid.Liquid;
import xieao.theora.lib.annotation.Initialize;

@Initialize
public class ILiquids {
    public static final Liquid ESSENCE;
    public static final Liquid LAVA;
    public static final Liquid MILK;

    static {
        ESSENCE = Liquid.register("essence", 0xF197FF, 0xF7C4FF);
        LAVA = Liquid.register("lava", 0xE8700A, 0xE8700A);
        MILK = Liquid.register("milk", 0xffffff, 0xffffff);
    }
}
