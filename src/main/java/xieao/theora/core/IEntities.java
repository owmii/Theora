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
        INTERACTOR = register("interactor", EntityInteractor.class, EntityInteractor::new);
    }

    static <T extends Entity> EntityType<T> register(String id, Class<? extends T> clazz, Function<? super World, ? extends T> factory) {
        EntityType<T> entityType = new EntityType<>(clazz, factory, true, true, null);
        entityType.setRegistryName(id);
        ENTITY_TYPES.add(entityType);
        return entityType;
    }

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<EntityType<?>> event) {
        ENTITY_TYPES.forEach(entityType -> event.getRegistry().register(entityType));
    }
}
