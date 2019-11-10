package zeroneye.theora.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import zeroneye.theora.Theora;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IEntities {
    public static final List<EntityType<?>> ENTITY_TYPES = new ArrayList<>();
    public static final EntityType<?> HOR = register("hor", EntityType.Builder.create(HorEntity::new, EntityClassification.MISC)
            .size(0.3F, 0.3F).setCustomClientFactory((spawnEntity, world) -> new HorEntity(world))
            .setUpdateInterval(4).setTrackingRange(10).setShouldReceiveVelocityUpdates(true));

    static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        EntityType<T> entityType = builder.build(Theora.MOD_ID + ":" + id);
        entityType.setRegistryName(id);
        ENTITY_TYPES.add(entityType);
        return entityType;
    }

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<EntityType<?>> event) {
        ENTITY_TYPES.forEach(entityType -> event.getRegistry().register(entityType));
    }
}
