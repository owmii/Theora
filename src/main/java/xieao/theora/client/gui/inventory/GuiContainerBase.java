package xieao.theora.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xieao.theora.inventory.ContainerBase;

@OnlyIn(Dist.CLIENT)
public abstract class GuiContainerBase<I extends IInventory, C extends ContainerBase<I>> extends GuiContainer {
    protected final I inv;
    protected final EntityPlayer player;
    protected int x, y;

    public GuiContainerBase(C container, EntityPlayer player, I inv) {
        super(container);
        this.inv = inv;
        this.player = player;
    }

    @Override
    protected void initGui() {
        super.initGui();
        this.x = (this.width - this.xSize) / 2;
        this.y = (this.height - this.ySize) / 2;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    public void bindTexture(ResourceLocation resource) {
        this.mc.getTextureManager().bindTexture(resource);
    }

    public I getInv() {
        return inv;
    }
}
