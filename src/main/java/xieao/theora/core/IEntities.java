package xieao.theora.core;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import xieao.theora.Theora;
import xieao.theora.core.lib.annotation.PreLoaded;
import xieao.theora.entity.EntityInteractor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@PreLoaded
public class IEntities {
    public static final Map<ResourceLocation, EntityType<?>> TYPES = new HashMap<>();
    public static final EntityType<EntityInteractor> INTERACTOR = register("interactor", EntityInteractor.class, EntityInteractor::new);

    public static <T extends Entity> EntityType<T> register(String id, Class<? extends T> clazz, Function<? super World, ? extends T> factory) {
        EntityType<T> entitytype = EntityType.Builder.create(clazz, factory).build(Theora.loc(id).toString());
        entitytype.setRegistryName(id);
        ForgeRegistries.ENTITIES.register(entitytype);
        TYPES.put(entitytype.getRegistryName(), entitytype);
        return entitytype;
    }
}
