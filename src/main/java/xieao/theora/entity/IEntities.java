package xieao.theora.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xieao.theora.Theora;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class IEntities {
    public static final Map<ResourceLocation, EntityType<?>> TYPES = new HashMap<>();
    public static final EntityType<EntityInteractor> INTERACTOR = register("interactor", EntityInteractor.class, EntityInteractor::new);

    public static <T extends Entity> EntityType<T> register(String id, Class<? extends T> clazz, Function<? super World, ? extends T> factory) {
        EntityType<T> entitytype = EntityType.Builder.create(clazz, factory).build(id);
        TYPES.put(Theora.loc(id), entitytype);
        return entitytype;
    }
}
