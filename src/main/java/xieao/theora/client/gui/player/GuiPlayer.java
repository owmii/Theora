package xieao.theora.client.gui.player;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xieao.theora.api.Consts;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.HorData;
import xieao.theora.client.gui.GuiBase;

@OnlyIn(Dist.CLIENT)
public class GuiPlayer extends GuiBase {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(Consts.MOD_ID, "textures/gui/background.png");
    private int x, y, w = 176, h = 139;

    @Override
    protected void initGui() {
        super.initGui();
        this.x = (this.width - this.w) / 2;
        this.y = (this.height - this.h) / 2;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        GlStateManager.pushMatrix();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translated(this.x, this.y, 0.0D);
        bindTexture(BACKGROUND);
        drawTexturedModalRect(0, 0, 0, 0, this.w, this.h);
        EntityPlayer player = this.mc.player;
        TheoraAPI.getPlayerData(player).ifPresent(playerData -> {
            HorData horData = playerData.hor;
            if (horData.loaded) {
                float stored = horData.getLiquidHandler().get("slot.essence").getStored();
                float cap = horData.getLiquidHandler().get("slot.essence").getCapacity();
                drawString(this.fontRenderer, "test: " + String.format("%.2f", stored) + "/" + String.format("%.2f", cap), 20, 20, 0xffffff);
            } else {
                drawString(this.fontRenderer, "Not loaded", 20, 20, 0xffffff);
            }
        });
        GlStateManager.popMatrix();
        super.render(mouseX, mouseY, partialTicks);
    }
}
