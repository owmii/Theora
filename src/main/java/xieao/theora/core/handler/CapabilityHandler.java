package xieao.theora.core.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xieao.theora.api.Consts;
import xieao.theora.api.liquid.LiquidHandler;
import xieao.theora.api.player.PlayerData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CapabilityHandler {
    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(Consts.MOD_ID, "player/data"), new PlayerDataProvider());
        }
    }

    public static class PlayerDataProvider implements ICapabilitySerializable<CompoundNBT> {
        private final PlayerData data = new PlayerData();
        private final LazyOptional<PlayerData> holder = LazyOptional.of(() -> this.data);

        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return PlayerData.Cap.DATA.orEmpty(cap, this.holder);
        }

        @Override
        public CompoundNBT serializeNBT() {
            return this.data.serialize();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            this.data.read(nbt);
        }
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(PlayerData.class, new Capability.IStorage<PlayerData>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<PlayerData> capability, PlayerData instance, Direction side) {
                return null;
            }

            @Override
            public void readNBT(Capability<PlayerData> capability, PlayerData instance, Direction side, INBT nbt) {
            }
        }, PlayerData::new);
        CapabilityManager.INSTANCE.register(LiquidHandler.class, new Storage<>(), LiquidHandler::new);
        CapabilityManager.INSTANCE.register(LiquidHandler.Item.class, new Storage<>(),
                () -> new LiquidHandler.Item(ItemStack.EMPTY));
    }

    private static class Storage<T extends LiquidHandler> implements Capability.IStorage<T> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<T> capability, T instance, Direction side) {
            return null;
        }

        @Override
        public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt) {
        }
    }

}
