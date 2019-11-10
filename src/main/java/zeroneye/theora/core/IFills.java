package zeroneye.theora.core;

import net.minecraftforge.fml.common.Mod;
import zeroneye.theora.api.fill.Fill;

@Mod.EventBusSubscriber
public class IFills {
    public static final Fill GLIOPHIN;

    static {
        GLIOPHIN = Fill.register("gliophin", 0x3e7c00, 0x3e9d00);
    }
}
