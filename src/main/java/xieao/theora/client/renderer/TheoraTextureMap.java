package xieao.theora.client.renderer;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.Theora;

@SuppressWarnings("NullableProblems")
public class TheoraTextureMap {

    public static TextureAtlasSprite liquid_interact_ov;

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerSprites(TextureStitchEvent.Pre event) {
        liquid_interact_ov = event.getMap().registerSprite(Theora.location("misc/liquid_interact_ov"));
    }

}
