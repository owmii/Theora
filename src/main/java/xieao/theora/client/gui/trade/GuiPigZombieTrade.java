package xieao.theora.client.gui.trade;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xieao.lib.util.ColorUtil;
import xieao.theora.Theora;
import xieao.theora.api.trade.pigzombie.PigZombieTrade;
import xieao.theora.common.item.TheoraItems;
import xieao.theora.network.TheoraNetwork;
import xieao.theora.network.packets.PacketPigZombieTradBuy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuiPigZombieTrade extends GuiScreen {

    private static final ResourceLocation BACKGROUND_TEXTURE = Theora.location("textures/instance/pig_zombie_trade.png");

    private UUID pigZombieId;
    private final List<PigZombieTrade> trades;
    private final int maxTrades;
    private int currTrade;

    private int x, y, w = 141, h = 82;

    public GuiPigZombieTrade(UUID pigZombieId, List<ResourceLocation> rls) {
        this.pigZombieId = pigZombieId;
        this.trades = new ArrayList<>();
        for (ResourceLocation rl : rls) {
            PigZombieTrade trade = PigZombieTrade.getPigZombieTrade(rl.toString());
            if (!trade.isEmpty()) {
                this.trades.add(trade);
            }
        }
        this.maxTrades = this.trades.size();
    }

    @Override
    public void initGui() {
        this.x = (this.width - this.w) / 2;
        this.y = (this.height - this.h) / 2;
        GuiButton next = new GuiButton(10, this.x + 88, this.y + 12, 20, 20, "");
        GuiButton prev = new GuiButton(11, this.x + 32, this.y + 12, 20, 20, "");
        GuiButton buy = new GuiButton(12, this.x + 60, this.y + 12, 20, 20, "");
        this.buttonList.add(next);
        this.buttonList.add(prev);
        this.buttonList.add(buy);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 10:
                if (this.currTrade < this.maxTrades - 1)
                    this.currTrade++;
                break;
            case 11:
                if (this.currTrade > 0)
                    this.currTrade--;
                break;
            case 12:
                TheoraNetwork.sendToServer(new PacketPigZombieTradBuy(this.trades.get(this.currTrade).getRegistryName()));
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        if (!this.trades.isEmpty()) {
            GlStateManager.pushMatrix();
            ColorUtil.glColorNormal();
            GlStateManager.enableBlend();
            GlStateManager.translate(this.x, this.y, 0.0D);
            this.mc.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
            drawTexturedModalRect(0, 0, 0, 0, this.w, this.h);
            ColorUtil.glColor(0xffffff, this.currTrade < this.maxTrades - 1 ? 1.0F : 0.3F);
            drawTexturedModalRect(87, 11, this.w, 0, 24, 20);
            ColorUtil.glColor(0xffffff, this.currTrade > 0 ? 1.0F : 0.3F);
            drawTexturedModalRect(29, 11, this.w + 24, 0, 24, 20);
            ColorUtil.glColorNormal();
            PigZombieTrade trade = this.trades.get(this.currTrade);
            ItemStack stack = trade.itemToSell;
            RenderItem renderItem = this.mc.getRenderItem();
            int sX = this.w / 2 - 8;
            int sY = this.h / 2 - 28;
            RenderHelper.enableGUIStandardItemLighting();
            renderItem.renderItemIntoGUI(stack, sX, sY);
            String ov = stack.getCount() == 1 ? "" : String.valueOf(stack.getCount());
            renderItem.renderItemOverlayIntoGUI(this.fontRenderer, stack, sX, sY, ov);
            renderItem.renderItemIntoGUI(new ItemStack(TheoraItems.PIG_COIN), sX - 12, sY + 30);
            this.fontRenderer.drawString(" x " + trade.price, sX + 2, sY + 33, 0x5a495a);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();

            if (inRange(this.x + sX, this.y + sY, mouseX, mouseY)) {
                renderToolTip(stack, mouseX, mouseY);
            }
        }
    }

    public boolean inRange(int x, int y, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + 16 && mouseY < y + 16;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)) {
            this.mc.player.closeScreen();
        }
    }
}
