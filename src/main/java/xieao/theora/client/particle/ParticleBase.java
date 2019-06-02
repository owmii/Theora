package xieao.theora.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import xieao.theora.core.lib.util.math.V3d;

public class ParticleBase extends Particle {
    protected V3d pos;

    public ParticleBase(World world, V3d pos) {
        this(world, pos.x, pos.y, pos.z);
        this.pos = pos;
    }

    public ParticleBase(World world, double x, double y, double z) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
    }
}
