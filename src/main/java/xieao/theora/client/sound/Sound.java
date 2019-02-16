package xieao.theora.client.sound;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Sound extends MovingSound {
    public Sound(SoundEvent sound, SoundCategory category) {
        super(sound, category);
    }

    @Override
    public void tick() {
    }

    public void stop() {
        this.donePlaying = true;
    }
}
