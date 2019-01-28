package xieao.theora.core;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xieao.theora.core.lib.util.Location;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ISounds {
    public static final List<SoundEvent> SOUNDS = new ArrayList<>();

    static SoundEvent register(String name) {
        Location id = Location.ROOT.get(name);
        SoundEvent sound = new SoundEvent(id);
        sound.setRegistryName(id);
        SOUNDS.add(sound);
        return sound;
    }

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<SoundEvent> event) {
        SOUNDS.forEach(sound -> event.getRegistry().register(sound));
    }
}
