package xieao.theora.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import xieao.theora.Theora;
import xieao.theora.network.packets.PacketRequestAbilitiesGui;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class KeyHandler {

    public static final KeyBinding KEY_ABILITIES = new KeyBinding("key.abilities.instance", Keyboard.KEY_B, "cat.theora");

    private final Minecraft mc = Minecraft.getMinecraft();

    public static void register() {
        ClientRegistry.registerKeyBinding(KEY_ABILITIES);
    }

    @SubscribeEvent
    public static void handleKeys(TickEvent.PlayerTickEvent event) {
        if (event.side == Side.CLIENT && event.phase == TickEvent.Phase.START) {
            if (KEY_ABILITIES.isPressed()) {
                Theora.NET.sendToServer(new PacketRequestAbilitiesGui());
            }
        }
    }
}
