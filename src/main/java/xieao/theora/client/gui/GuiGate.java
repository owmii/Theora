package xieao.theora.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import xieao.theora.api.Consts;
import xieao.theora.block.gate.TileGate;
import xieao.theora.client.gui.inventory.GuiContainerBase;
import xieao.theora.inventory.ContainerGate;

public class GuiGate extends GuiContainerBase<TileGate, ContainerGate> {
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Consts.MOD_ID, "textures/gui/container/gate.png");

    public GuiGate(EntityPlayer player, TileGate inv) {
        super(new ContainerGate(player, inv), player, inv);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
        this.drawTexturedModalRect(this.x, this.y, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

    }
}
