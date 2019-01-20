package xieao.theora.core;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;
import xieao.theora.Theora;
import xieao.theora.core.lib.annotation.PreLoad;

import java.util.ArrayList;
import java.util.List;

@PreLoad
public class ISounds {
    public static final List<SoundEvent> SOUNDS = new ArrayList<>();

    static SoundEvent register(String name) {
        SoundEvent sound = new SoundEvent(Theora.loc(name));
        sound.setRegistryName(sound.getName());
        ForgeRegistries.SOUND_EVENTS.register(sound);
        SOUNDS.add(sound);
        return sound;
    }
}
