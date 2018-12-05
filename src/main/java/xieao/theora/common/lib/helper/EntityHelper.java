package xieao.theora.common.lib.helper;

import net.minecraft.entity.Entity;

public class EntityHelper {

    public static boolean isMoving(Entity entity) {
        return entity.motionX != 0.0D || entity.motionY != 0.0D || entity.motionZ != 0.0D;
    }

}
