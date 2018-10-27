package xieao.theora.api.liquid;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class LiquidContainerCapability {

    @CapabilityInject(IliquidContainer.class)
    public static Capability<IliquidContainer> CAPABILITY_LIQUID_CONTAINER = null;
    @CapabilityInject(IliquidContainer.Item.class)
    public static Capability<IliquidContainer.Item> CAPABILITY_LIQUID_CONTAINER_ITEM = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IliquidContainer.class, new DefaultLiquidHandlerStorage<>(), LiquidContainer::new);
        CapabilityManager.INSTANCE.register(IliquidContainer.Item.class, new DefaultLiquidHandlerStorage<>(),
                () -> new LiquidContainer.Item(ItemStack.EMPTY));
    }

    private static class DefaultLiquidHandlerStorage<T extends IliquidContainer> implements Capability.IStorage<T> {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
            return null;
        }

        @Override
        public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {

        }
    }
}
