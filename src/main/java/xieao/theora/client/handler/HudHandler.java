package xieao.theora.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.data.PlayerData;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class HudHandler {

    @SubscribeEvent
    public static void renderHud(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayerSP player = mc.player;

            int width = event.getResolution().getScaledWidth();
            int height = event.getResolution().getScaledHeight();

            PlayerData data = TheoraAPI.getPlayerData(player);
            if (data != null) {
//                if (data.hasAcidVial()) {
//                    float max = data.getMaxAcid();
//                    float cur = data.getStoredAcid();
//                    mc.fontRenderer.drawString(cur + "/" + max, 0, 0, 0xffffff);
//                }
            }
        }
    }
}
