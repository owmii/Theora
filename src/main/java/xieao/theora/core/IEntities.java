package xieao.theora.core;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xieao.theora.entity.EntityInteractor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IEntities {
    public static final List<EntityType<?>> ENTITY_TYPES = new ArrayList<>();
    public static final EntityType<?> INTERACTOR;

    static {
        INTERACTOR = register("interactor", EntityInteractor.class, EntityInteractor::new, 10, 64, false);
    }

    static <T extends Entity> EntityType<T> register(String id, Class<? extends T> clazz, Function<? super World, ? extends T> factory, int range, int freq, boolean sendVelocity) {
        return register(id, EntityType.Builder.create(clazz, factory)
                .tracker(range, freq, sendVelocity));
    }

    static <T extends Entity> EntityType<T> register(String id, Class<? extends T> clazz, Function<? super World, ? extends T> factory) {
        return register(id, EntityType.Builder.create(clazz, factory));
    }

    static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        EntityType<T> entityType = builder.disableSerialization().build(id);
        entityType.setRegistryName(id);
        ENTITY_TYPES.add(entityType);
        return entityType;
    }

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<EntityType<?>> event) {
        ENTITY_TYPES.forEach(entityType -> event.getRegistry().register(entityType));
    }
}
