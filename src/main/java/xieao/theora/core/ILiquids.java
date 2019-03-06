package xieao.theora.core;

import xieao.theora.api.liquid.Liquid;
import xieao.theora.lib.annotation.Initialize;

@Initialize
public class ILiquids {
    public static final Liquid WATER = Liquid.register("water", 0x3165E8, 0x3165E8);
    public static final Liquid LAVA = Liquid.register("lava", 0xE8700A, 0xE8700A);
    public static final Liquid MILK = Liquid.register("milk", 0xffffff, 0xffffff);
}
