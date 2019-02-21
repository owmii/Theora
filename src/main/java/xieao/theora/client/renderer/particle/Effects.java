package xieao.theora.client.renderer.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;
import xieao.theora.api.Consts;
import xieao.theora.client.core.lib.util.ColorUtil;
import xieao.theora.core.lib.util.math.V3d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(Dist.CLIENT)
public final class Effects {
    public static final Effects INSTANCE = new Effects();
    private final List<Base> effects = new ArrayList<>();
    private final Minecraft mc = Minecraft.getInstance();

    public static Base create(Texture texture, World world, V3d origin) {
        return new Base(texture, world, origin);
    }

    public void addEffect(Base effect) {
        if (this.mc.currentScreen == null || !this.mc.currentScreen.doesGuiPauseGame()) {
            this.effects.add(effect);
        }
    }

    @SubscribeEvent
    public static void tickParticles(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.currentScreen == null || !mc.currentScreen.doesGuiPauseGame()) {
                Iterator<Base> iterator = INSTANCE.effects.iterator();
                while (iterator.hasNext()) {
                    Base particle = iterator.next();
                    particle.tick();
                    if (!particle.isAlive()) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void renderParticles(RenderWorldLastEvent event) {
        float f0 = ActiveRenderInfo.getRotationX();
        float f1 = ActiveRenderInfo.getRotationZ();
        float f2 = ActiveRenderInfo.getRotationYZ();
        float f3 = ActiveRenderInfo.getRotationXY();
        float f4 = ActiveRenderInfo.getRotationXZ();
        EntityPlayerSP player = Minecraft.getInstance().player;
        float partialTicks = event.getPartialTicks();
        Particle.interpPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
        Particle.interpPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
        Particle.interpPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;
        Particle.cameraViewDir = player.getLook(partialTicks);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(516, 0.003921569F);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        for (int i = 0; i < INSTANCE.effects.size(); i++) {
            Base particle = INSTANCE.effects.get(i);
            GlStateManager.depthMask(!particle.shouldDisableDepth());
            particle.render(partialTicks, f0, f4, f1, f2, f3);
        }
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1F);
    }

    public static class Base extends Particle {
        protected Texture texture;
        protected V3d origin;
        protected V3d to;
        protected int textureID;
        protected boolean noDepth;
        protected boolean blendFunc;
        protected boolean bright;
        protected double speed;
        protected int alphaMode;
        protected int scaleMode;
        protected float scaleFactor;
        protected boolean dynamicColor;
        protected int fromColor;
        protected int toColor;

        public Base(Texture texture, World world, V3d origin) {
            super(world, origin.x, origin.y, origin.z);
            this.texture = texture;
            this.origin = origin;
            this.to = origin;
            this.noDepth = true;
            this.speed = 0.2D;
            this.particleScale = 1.0F;
            this.particleAlpha = 0.0F;
            this.canCollide = false;
            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
            color(0xffffff);
            if (texture.frames > 1 && texture.randomize) {
                this.textureID = this.rand.nextInt(texture.frames);
            }
        }

        public void render(float partialTicks, double rotX, double rotZ, double rotYZ, double rotXY, double rotXZ) {
            double d0 = 0.1F * this.particleScale; //TODO particle rotation
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
            String textureSuffix = this.texture.frames > 1 ? "" + this.textureID : "";
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(Consts.MOD_ID, "textures/particles/" + this.texture.name + textureSuffix + ".png"));
            if (this.blendFunc) {
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            }
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
            bufferbuilder.pos(d1 + posVec[0].x, d2 + posVec[0].y, d3 + posVec[0].z).tex(1.0D, 1.0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
            bufferbuilder.pos(d1 + posVec[1].x, d2 + posVec[1].y, d3 + posVec[1].z).tex(1.0D, 0.0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
            bufferbuilder.pos(d1 + posVec[2].x, d2 + posVec[2].y, d3 + posVec[2].z).tex(0.0D, 0.0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
            bufferbuilder.pos(d1 + posVec[3].x, d2 + posVec[3].y, d3 + posVec[3].z).tex(0.0D, 1.0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
            tessellator.draw();
            if (this.blendFunc) {
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            }
        }

        @Override
        public void tick() {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            if (this.texture.frames > 1 && !this.texture.randomize) {
                this.textureID = this.age * this.texture.frames / this.maxAge;
            }
            float f0 = (float) this.age / (float) this.maxAge;
            if (this.dynamicColor) {
                color(ColorUtil.blend(this.fromColor, this.toColor, f0));
            }
            this.particleAlpha = this.alphaMode == 1 ? (1.0F - f0) : this.particleAlpha;
            if (this.scaleMode == 3) {
                this.particleScale = MathHelper.sin((float) (f0 * Math.PI));
            } else if (this.scaleMode == 2) {
                this.particleScale *= this.scaleFactor;
            } else if (this.scaleMode == 1) {
                this.particleScale = 1.0F - f0;
            }
            if (!this.origin.equals(this.to)) {
                this.motionX = (this.to.x - this.posX) * this.speed;
                this.motionY = (this.to.y - this.posY) * this.speed;
                this.motionZ = (this.to.z - this.posZ) * this.speed;
            }
            this.motionY = (double) -this.particleGravity;
            move(this.motionX, this.motionY, this.motionZ);
            if (this.age++ > this.maxAge) {
                setExpired();
            }
        }

        public Base maxAge(int maxAge) {
            this.maxAge = maxAge;
            return this;
        }

        public Base to(V3d to) {
            this.to = to;
            return this;
        }

        public Base speed(double speed) {
            this.speed = speed;
            return this;
        }

        public Base color(int color) {
            this.particleRed = (float) (color >> 16) / 255.0F;
            this.particleGreen = (float) (color >> 8 & 255) / 255.0F;
            this.particleBlue = (float) (color & 255) / 255.0F;
            return this;
        }

        public Base color(int from, int to) {
            this.fromColor = from;
            this.toColor = to;
            this.dynamicColor = true;
            return color(from);
        }

        public Base alpha(float alpha) {
            return alpha(alpha, 0);
        }

        public Base alpha(float alpha, int mode) {
            this.particleAlpha = alpha;
            this.alphaMode = mode;
            return this;
        }

        /**
         * Mod: 0 = normal <br />
         * Mod: 1 = 1.0F - ageFactor <br />
         * Mod: 2 = scale * scaleFactor <br />
         * Mod: 3 = blob <br />
         */

        public Base scale(float scale, int mode, float scaleFactor) {
            this.particleScale = mode == 3 ? 0 : scale;
            this.scaleMode = mode;
            this.scaleFactor = scaleFactor;
            return this;
        }

        public Base blendFunc() {
            this.blendFunc = true;
            return this;
        }

        public Base depth() {
            this.noDepth = false;
            return this;
        }

        public Base collide() {
            this.canCollide = true;
            return this;
        }

        public Base gravity(Float gravity) {
            this.particleGravity = gravity;
            return this;
        }

        @Override
        public boolean shouldDisableDepth() {
            return this.noDepth;
        }

        @Override
        public int getBrightnessForRender(float partialTick) {
            return this.bright ? 15728880 : super.getBrightnessForRender(partialTick);
        }

        public void spawn() {
            spawn(1);
        }

        public void spawn(int count) {
            for (int i = 0; i < count; i++) {
                INSTANCE.addEffect(this);
            }
        }
    }

    public static final Texture GLOW_SMALL = new Texture("glow_small");
    public static final Texture GLOW_MID = new Texture("glow_mid");
    public static final Texture GLOW_DENS = new Texture("glow_dens");
    public static final Texture SQUARE = new Texture("square");
    public static final Texture STAR = new Texture("star");
    public static final Texture RUNES = new Texture("runes/", 25, true);

    public static class Texture {
        public final String name;
        public final int frames;
        public final boolean randomize;

        public Texture(String name) {
            this(name, 0, false);
        }

        public Texture(String name, int frames) {
            this(name, frames, false);
        }

        public Texture(String name, int frames, boolean randomize) {
            this.name = name;
            this.frames = frames;
            this.randomize = randomize;
        }
    }
}