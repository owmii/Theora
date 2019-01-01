package xieao.theora.client.renderer;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.Theora;

@SuppressWarnings("NullableProblems")
public class TheoraTextures {

    public static TextureAtlasSprite LIQUID_INTERACT_OV;

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void register(TextureStitchEvent.Pre event) {
        LIQUID_INTERACT_OV = event.getMap().registerSprite(Theora.location("misc/liquid_interact_ov"));
    }
}
