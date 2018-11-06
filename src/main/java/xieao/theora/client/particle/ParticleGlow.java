package xieao.theora.client.particle;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleGlow extends ParticleBase {

    public ParticleGlow(World world, Vec3d start, Vec3d end, double speed, int maxAge, float scale, int color, float alpha) {
        super(world, start, end, speed, maxAge, scale, color, alpha);
        this.particleTexture = "glow0";
        // this.particleMaxAge = (int) (80.0D / (Math.random() * 0.8D + 0.2D));
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        move(this.motionX, this.motionY, this.motionZ);
        this.particleAlpha *= 0.92F;
        this.particleScale *= 0.98F;
        this.motionX = (end.x - this.posX) * 0.04;
        this.motionY = (end.y - this.posY) * 0.04;
        this.motionZ = (end.z - this.posZ) * 0.04;
        if (this.particleAge++ >= this.particleMaxAge) {
            setExpired();
        }
    }

    @Override
    public void renderParticle(float partialTicks, double rotX, double rotZ, double rotYZ, double rotXY, double rotXZ) {
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        super.renderParticle(partialTicks, rotX, rotZ, rotYZ, rotXY, rotXZ);
    }
}
