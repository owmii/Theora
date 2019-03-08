package xieao.theora.api.liquid;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import xieao.theora.api.Consts;
import xieao.theora.api.registry.RegistryEntry;

import javax.annotation.Nullable;
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

    public static Liquid read(String key, NBTTagCompound compound) {
        NBTTagCompound nbt = compound.getCompound(key);
        return get(nbt.getString("LiquidId"));
    }

    public static Liquid read(NBTTagCompound compound) {
        return get(compound.getString("LiquidId"));
    }

    public static void write(String key, Liquid liquid, NBTTagCompound compound) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.putString("LiquidId", liquid.getString());
        compound.put(key, nbt);
    }

    public static Liquid get(String name) {
        Liquid liquid = REGISTRY.get(new ResourceLocation(name));
        return liquid == null ? EMPTY : liquid;
    }

    public static void write(Liquid liquid, NBTTagCompound compound) {
        compound.putString("LiquidId", liquid.getString());
    }

    @OnlyIn(Dist.CLIENT)
    public String getDisplayName() {
        return I18n.format("liquid." + getString().replace(':', '.'));
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
        @CapabilityInject(LiquidHandler.class)
        public static Capability<LiquidHandler> HANDLER = null;
        @CapabilityInject(LiquidHandler.Item.class)
        public static Capability<LiquidHandler.Item> HANDLER_ITEM = null;

        public static void register() {
            CapabilityManager.INSTANCE.register(LiquidHandler.class, new Storage<>(), LiquidHandler::new);
            CapabilityManager.INSTANCE.register(LiquidHandler.Item.class, new Storage<>(),
                    () -> new LiquidHandler.Item(ItemStack.EMPTY));
        }

        private static class Storage<T extends LiquidHandler> implements Capability.IStorage<T> {
            @Nullable
            @Override
            public INBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
                return null;
            }

            @Override
            public void readNBT(Capability<T> capability, T instance, EnumFacing side, INBTBase nbt) {
            }
        }
    }
}
