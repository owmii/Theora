package xieao.theora.client.handler;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class HudHandler {

    @SubscribeEvent
    public static void preRender(RenderGameOverlayEvent.Pre event) {
    }

    @SubscribeEvent
    public static void render(RenderGameOverlayEvent event) {
    }

    @SubscribeEvent
    public static void postRender(RenderGameOverlayEvent.Post event) {
    }


}
