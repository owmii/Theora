package xieao.theora.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import xieao.theora.Theora;

@SideOnly(Side.CLIENT)
public class ParticleGeneric extends Particle {

    protected ParticleTetxure tetxure;
    protected Vec3d start;
    protected Vec3d end;
    protected double speed;
    protected int alphaMode;
    protected int scaleMode;
    protected float scaleFactor;
    protected float rotSpeed;
    protected float rotX;
    protected float rotY;
    protected float rotZ;

    public ParticleGeneric(ParticleTetxure tetxure, World world, Vec3d start, int maxAge) {
        super(world, start.x, start.y, start.z);
        this.start = start;
        this.end = start;
        this.speed = 0.2D;
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.particleMaxAge = maxAge;
        this.particleScale = 1.0F;
        setColor(0xffffff);
        this.particleAlpha = 0.0F;
        this.tetxure = tetxure;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float f0 = (float) this.particleAge / (float) this.particleMaxAge;
        this.particleAlpha = this.alphaMode == 1 ? (1.0F - f0) : this.particleAlpha;
        this.particleScale = this.scaleMode == 1 ? (1.0F - f0) : this.scaleMode == 2 ? this.particleScale *= this.scaleFactor : this.particleScale;
        if (!this.start.equals(this.end)) {
            this.motionX = (end.x - this.posX) * this.speed;
            this.motionY = (end.y - this.posY) * this.speed;
            this.motionZ = (end.z - this.posZ) * this.speed;
            move(this.motionX, this.motionY, this.motionZ);
        }
        killExpiredParticle();
    }

    public void renderParticle(float partialTicks, double rotX, double rotZ, double rotYZ, double rotXY, double rotXZ) {
        double d0 = 0.1F * this.particleScale;
        double d1 = this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX;
        double d2 = this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY;
        double d3 = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ;
        int i = getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;
        Vec3d[] posVec = new Vec3d[]{new Vec3d(-rotX * d0 - rotXY * d0, -rotZ * d0, -rotYZ * d0 - rotXZ * d0), new Vec3d(-rotX * d0 + rotXY * d0, rotZ * d0, -rotYZ * d0 + rotXZ * d0), new Vec3d(rotX * d0 + rotXY * d0, rotZ * d0, rotYZ * d0 + rotXZ * d0), new Vec3d(rotX * d0 - rotXY * d0, -rotZ * d0, rotYZ * d0 - rotXZ * d0)};
        if (this.particleAngle != 0.0F) {
            double d4 = this.particleAngle + (this.particleAngle - this.prevParticleAngle) * partialTicks;
            double d5 = MathHelper.cos((float) (d4 * 0.5F));
            double d6 = MathHelper.sin((float) (d4 * 0.5F)) * cameraViewDir.x;
            double d7 = MathHelper.sin((float) (d4 * 0.5F)) * cameraViewDir.y;
            double d8 = MathHelper.sin((float) (d4 * 0.5F)) * cameraViewDir.z;
            Vec3d vec3d = new Vec3d(d6, d7, d8);
            for (int l = 0; l < 4; ++l) {
                posVec[l] = vec3d.scale(2.0D * posVec[l].dotProduct(vec3d)).add(posVec[l].scale(d5 * d5 - vec3d.dotProduct(vec3d))).add(vec3d.crossProduct(posVec[l]).scale(2.0F * d5));
            }
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(Theora.location("textures/particles/" + this.tetxure.name + ".png"));
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        bufferbuilder.pos(d1 + posVec[0].x, d2 + posVec[0].y, d3 + posVec[0].z).tex(1.0D, 1.0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        bufferbuilder.pos(d1 + posVec[1].x, d2 + posVec[1].y, d3 + posVec[1].z).tex(1.0D, 0.0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        bufferbuilder.pos(d1 + posVec[2].x, d2 + posVec[2].y, d3 + posVec[2].z).tex(0.0D, 0.0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        bufferbuilder.pos(d1 + posVec[3].x, d2 + posVec[3].y, d3 + posVec[3].z).tex(0.0D, 1.0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        tessellator.draw();
    }

    protected void killExpiredParticle() {
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setExpired();
        }
    }

    public ParticleGeneric setEnd(Vec3d end) {
        this.end = end;
        return this;
    }

    public ParticleGeneric setSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    public ParticleGeneric setAlpha(float alpha, int mode) {
        this.particleAlpha = alpha;
        this.alphaMode = mode;
        return this;
    }

    public ParticleGeneric scale(float scale, int mode, float scaleFactor) {
        this.particleScale = scale;
        this.scaleMode = mode;
        this.scaleFactor = scaleFactor;
        return this;
    }

    public ParticleGeneric rotate(float rotSpeed, float rotX, float rotY, float rotZ) {
        this.rotSpeed = rotSpeed;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        return this;
    }

    public void setColor(int color) {
        this.particleRed = (float) (color >> 16) / 255.0F;
        this.particleGreen = (float) (color >> 8 & 255) / 255.0F;
        this.particleBlue = (float) (color & 255) / 255.0F;
    }

    @Override
    public boolean shouldDisableDepth() {
        return true;
    }
}
