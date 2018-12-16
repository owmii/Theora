package xieao.theora.client.particle;

public enum ParticleTexture {
    GLOW_SMALL("glow_small", 0, false),
    GLOW_MID("glow_mid", 0, false),
    GLOW_DENS("glow_dens", 0, false),
    SQUARE("square", 0, false),
    STAR("star", 0, false),
    RUNES("runes/", 25, true);

    public final String name;
    public final int frames;
    public final boolean randomize;

    ParticleTexture(String name, int frames, boolean randomize) {
        this.name = name;
        this.frames = frames;
        this.randomize = randomize;
    }
}
