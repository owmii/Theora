package xieao.theora.client.gui.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import xieao.theora.api.Consts;
import xieao.theora.block.hor.TileHor;
import xieao.theora.inventory.ContainerGate;

public class GuiHor extends GuiContainerBase<TileHor, ContainerGate> {
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Consts.MOD_ID, "textures/gui/container/hor.png");

    public GuiHor(EntityPlayer player, TileHor inv) {
        super(new ContainerGate(player, inv), player, inv);
        this.xSize = 164;
        this.ySize = 93;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
        this.drawTexturedModalRect(this.x, this.y, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawString(fontRenderer, "Stored ", 0, 0, 0xffffff);
    }
}