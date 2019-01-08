package xieao.theora;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod(Theora.MOD_ID)
public class Theora {

    public static final String MOD_ID = "theora";

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
