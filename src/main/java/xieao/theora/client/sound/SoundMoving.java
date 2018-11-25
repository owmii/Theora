package xieao.theora.client.sound;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundMoving extends MovingSound {

    protected SoundMoving(SoundEvent soundIn, SoundCategory categoryIn) {
        super(soundIn, categoryIn);
    }

    @Override
    public void update() {

    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    @Override
    public boolean isDonePlaying() {
        return donePlaying;
    }

    public void play() {
        this.donePlaying = false;
    }

    public void stop() {
        this.donePlaying = true;
    }


}
