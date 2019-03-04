package xieao.theora.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiBase extends GuiScreen {
    public void bindTexture(ResourceLocation resource) {
        this.mc.getTextureManager().bindTexture(resource);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
