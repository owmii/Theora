package xieao.theora.core;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import xieao.theora.core.lib.annotation.PreLoad;
import xieao.theora.entity.EntityInteractor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@PreLoad
public class IEntities {
    public static final Map<ResourceLocation, EntityType<?>> TYPES = new HashMap<>();
    public static final EntityType<?> INTERACTOR;

    static {
        INTERACTOR = register("interactor", EntityInteractor.class, EntityInteractor::new);
    }

    static <T extends Entity> EntityType<T> register(String id, Class<? extends T> clazz, Function<? super World, ? extends T> factory) {
        EntityType<T> entitytype = new EntityType<>(clazz, factory, true, true, null);
        entitytype.setRegistryName(id);
        ForgeRegistries.ENTITIES.register(entitytype);
        TYPES.put(entitytype.getRegistryName(), entitytype);
        return entitytype;
    }
}
