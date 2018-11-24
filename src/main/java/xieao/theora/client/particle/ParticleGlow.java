package xieao.theora.client.particle;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleGlow extends ParticleBase {

    public ParticleGlow(World world, Vec3d start, Vec3d end, double speed, int maxAge, float scale, int color, float alpha) {
        super(world, start, end, speed, maxAge, scale, color, alpha);
        this.particleTexture = "glow0";
        this.particleAlpha = 0;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        float f0 = (float) this.particleAge / (float) this.particleMaxAge;
        this.particleAlpha = (1.0F - f0);
        this.particleScale *= 0.90F;
        this.motionX = (end.x - this.posX) * this.speed;
        this.motionY = (end.y - this.posY) * this.speed;
        this.motionZ = (end.z - this.posZ) * this.speed;
        move(this.motionX, this.motionY, this.motionZ);
    }

    @Override
    public void renderParticle(float partialTicks, double rotX, double rotZ, double rotYZ, double rotXY, double rotXZ) {
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        super.renderParticle(partialTicks, rotX, rotZ, rotYZ, rotXY, rotXZ);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }
}
