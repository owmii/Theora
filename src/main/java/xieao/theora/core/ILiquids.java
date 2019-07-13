package xieao.theora.core;

import net.minecraftforge.fml.common.Mod;
import xieao.theora.api.liquid.Liquid;

@Mod.EventBusSubscriber
public class ILiquids {
    public static final Liquid GLIOPHIN;

    static {
        GLIOPHIN = Liquid.register("gliophin", 0x3e7c00, 0x3e9d00);
    }
}
