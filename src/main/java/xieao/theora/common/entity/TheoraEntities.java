package xieao.theora.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import xieao.theora.Theora;

public class TheoraEntities {

    static int id;

    public static void register() {
        register(EntityInteractor.class, "interactor", 64, 10, false);
        register(EntitySoul.class, "soul", 64, 10, true);
    }

    private static void register(Class<? extends Entity> entityClass, String entityName, int updateFrequency, int trackingRange, boolean sendsVelocityUpdates) {
        ResourceLocation registryName = new ResourceLocation(Theora.MOD_ID, entityName);
        EntityRegistry.registerModEntity(registryName, entityClass, entityName, id++, Theora.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
    }
}
