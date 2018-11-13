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
public class ParticleBase extends Particle {

    protected final Vec3d start;
    protected final Vec3d end;
    protected final double speed;
    protected String particleTexture;

    public ParticleBase(World world, Vec3d start, Vec3d end, double speed, int maxAge, float scale, int color, float alpha) {
        super(world, start.x, start.y, start.z);
        this.start = start;
        this.end = end;
        this.speed = speed;
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.particleMaxAge = maxAge;
        this.particleScale = scale;
        setColor(color);
        this.particleAlpha = alpha;
        this.particleTexture = "glow2";
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        move(this.motionX, this.motionY, this.motionZ);
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
        Minecraft.getMinecraft().getTextureManager().bindTexture(Theora.location("textures/particles/" + this.particleTexture + ".png"));
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

    protected void setColor(int color) {
        this.particleRed = (float) (color >> 16) / 255.0F;
        this.particleGreen = (float) (color >> 8 & 255) / 255.0F;
        this.particleBlue = (float) (color & 255) / 255.0F;
    }

    @Override
    public boolean shouldDisableDepth() {
        return true;
    }
}
