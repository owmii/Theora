package zeroneye.theora.core;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import zeroneye.theora.Theora;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ISounds {
    public static final List<SoundEvent> SOUNDS = new ArrayList<>();

    static SoundEvent register(String name) {
        ResourceLocation id = new ResourceLocation(Theora.MOD_ID, name);
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
