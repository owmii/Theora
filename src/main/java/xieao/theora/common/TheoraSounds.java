package xieao.theora.common;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import xieao.theora.Theora;

public class TheoraSounds {

    public static final SoundEvent BLOCK_TRANSFORMED = new SoundEvent(Theora.location("block.transformed"));
    public static final SoundEvent WHOOSH_FIRE = new SoundEvent(Theora.location("whoosh.fire"));
    public static final SoundEvent WHOOSH_SMOKE = new SoundEvent(Theora.location("whoosh.smoke"));
    public static final SoundEvent POK = new SoundEvent(Theora.location("pok"));
    public static final SoundEvent LIQUID_DRIP = new SoundEvent(Theora.location("liquid.drip"));
    public static final SoundEvent PEEL = new SoundEvent(Theora.location("peel"));
    public static final SoundEvent COINS_COLLECT = new SoundEvent(Theora.location("coins.collect"));


    public String[] register() {
        register(BLOCK_TRANSFORMED);
        register(WHOOSH_FIRE);
        register(WHOOSH_SMOKE);
        register(POK);
        register(LIQUID_DRIP);
        register(PEEL);
        register(COINS_COLLECT);
        return new String[0];
    }

    public SoundEvent register(SoundEvent soundEvent) {
        soundEvent.setRegistryName(soundEvent.getSoundName());
        ForgeRegistries.SOUND_EVENTS.register(soundEvent);
        return soundEvent;
    }
}