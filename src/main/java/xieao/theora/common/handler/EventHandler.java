package xieao.theora.common.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xieao.theora.Theora;
import xieao.theora.api.player.data.PlayerDataProvider;

@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(Theora.location("player.data"), new PlayerDataProvider());
        }
    }
}
