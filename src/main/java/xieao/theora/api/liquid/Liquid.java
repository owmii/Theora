package xieao.theora.api.liquid;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import xieao.theora.api.registry.RegistryEntry;

import java.util.HashMap;
import java.util.Map;

public class Liquid extends RegistryEntry<Liquid> {

    public static final Map<ResourceLocation, Liquid> REGISTRY;
    public static final Liquid EMPTY;

    private int darkColor;
    private int brightColor;

    static {
        REGISTRY = new HashMap<>();
        EMPTY = register("theora:empty", 0x000000, 0xffffff);
    }

    public static Liquid register(String name, int darkColor, int brightColor) {
        Liquid liquid = new Liquid();
        register(liquid, name, darkColor, brightColor);
        return liquid;
    }

    public static Liquid register(Liquid liquid, String name, int darkColor, int brightColor) {
        liquid.setRegistryName(name);
        liquid.setDarkColor(darkColor);
        liquid.setBrightColor(brightColor);
        REGISTRY.put(liquid.getRegistryName(), liquid);
        return liquid;
    }


    public static Liquid getLiquid(String name) {
        Liquid liquid = REGISTRY.get(new ResourceLocation(name));
        return liquid == null ? EMPTY : liquid;
    }

    public static Liquid readNBT(NBTTagCompound compound) {
        return getLiquid(compound.getString("regName"));
    }

    public static void writeNBT(Liquid liquid, NBTTagCompound compound) {
        compound.setString("regName", liquid.getRegistryString());
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public int getDarkColor() {
        return darkColor;
    }

    public void setDarkColor(int darkColor) {
        this.darkColor = darkColor;
    }

    public int getBrightColor() {
        return brightColor;
    }

    public void setBrightColor(int brightColor) {
        this.brightColor = brightColor;
    }

}
