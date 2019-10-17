package xieao.theora.api.fill;

import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import xieao.theora.api.registry.RegistryEntry;

import java.util.HashMap;
import java.util.Map;

public class Fill extends RegistryEntry<Fill> {
    public static final Map<ResourceLocation, Fill> REGISTRY = new HashMap<>();
    public static final Fill EMPTY = register("theora:empty", 0, 0);
    private int color0;
    private int color1;

    public static Fill register(String name, int color0, int color1) {
        Fill fill = new Fill();
        fill.setRegistryName(name);
        fill.color0 = color0;
        fill.color1 = color1;
        REGISTRY.put(fill.getRegistryName(), fill);
        return fill;
    }

    public static Fill read(String key, CompoundNBT compound) {
        CompoundNBT nbt = compound.getCompound(key);
        return get(nbt.getString("LiquidId"));
    }

    public static Fill read(CompoundNBT compound) {
        return get(compound.getString("LiquidId"));
    }

    public static void write(String key, Fill fill, CompoundNBT compound) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("LiquidId", fill.getString());
        compound.put(key, nbt);
    }

    public static Fill get(String name) {
        Fill fill = REGISTRY.get(new ResourceLocation(name));
        return fill == null ? EMPTY : fill;
    }

    public static void write(Fill fill, CompoundNBT compound) {
        compound.putString("LiquidId", fill.getString());
    }

    @OnlyIn(Dist.CLIENT)
    public String getDisplayName() {
        return I18n.format("fill." + getString().replace(':', '.'));
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public int getColor0() {
        return color0;
    }

    public int getColor1() {
        return color1;
    }

    public static final class Cap {
        @CapabilityInject(FillHandler.class)
        public static Capability<FillHandler> HANDLER = null;
        @CapabilityInject(FillHandler.Item.class)
        public static Capability<FillHandler.Item> HANDLER_ITEM = null;
    }
}
