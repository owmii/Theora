package xieao.theora.core.handler;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xieao.theora.api.player.HorData;

@Mod.EventBusSubscriber
public class ServerHandler {
    @SubscribeEvent
    public static void tick(TickEvent.ServerTickEvent event) {
        HorData.current++;
    }
}
