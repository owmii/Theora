package xieao.theora.core;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xieao.theora.Theora;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IEntities {
    public static final List<EntityType<?>> ENTITY_TYPES = new ArrayList<>();

    static {

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
