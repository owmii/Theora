package xieao.theora.client.particle;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleBlobGLow extends ParticleBase {

    public ParticleBlobGLow(World world, Vec3d start, Vec3d end, double speed, int maxAge, float scale, int color, float alpha) {
        super(world, start, end, speed, maxAge, scale, color, alpha);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }
}
