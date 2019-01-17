package xieao.theora.core;

import net.minecraft.util.SoundEvent;
import xieao.theora.Theora;

import java.util.ArrayList;
import java.util.List;

public class ISounds {
    public static final List<SoundEvent> SOUNDS = new ArrayList<>();

    private static SoundEvent register(String name) {
        SoundEvent sound = new SoundEvent(Theora.loc(name));
        sound.setRegistryName(sound.getName());
        SOUNDS.add(sound);
        return sound;
    }
}
