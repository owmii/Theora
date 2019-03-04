package xieao.theora.api.liquid;

import net.minecraft.util.ResourceLocation;
import xieao.theora.api.Consts;
import xieao.theora.api.registry.RegistryEntry;

import java.util.HashMap;
import java.util.Map;

public class Liquid extends RegistryEntry<Liquid> {
    public static final Map<ResourceLocation, Liquid> REGISTRY = new HashMap<>();
    public static final Liquid EMPTY = register(Consts.EMPTY, 0, 0);
    private int color0;
    private int color1;

    public static Liquid register(String name, int color0, int color1) {
        Liquid liquid = new Liquid();
        liquid.setRegistryName(name);
        liquid.color0 = color0;
        liquid.color1 = color1;
        REGISTRY.put(liquid.getRegistryName(), liquid);
        return liquid;
    }
}
