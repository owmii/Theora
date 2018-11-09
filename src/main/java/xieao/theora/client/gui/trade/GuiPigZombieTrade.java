package xieao.theora.client.gui.trade;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xieao.theora.Theora;
import xieao.theora.api.trade.pigzombie.PigZombieTrade;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuiPigZombieTrade extends GuiScreen {

    private static final ResourceLocation BACKGROUNG_TEXTURE = Theora.location("textures/gui/pig_zombie_trade.png");

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
    }

    @Override
    protected void actionPerformed(GuiButton button) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        if (!this.trades.isEmpty()) {
            mc.getRenderItem().renderItemIntoGUI(this.trades.get(0).itemToSell, 0, 0);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void renderToolTip(ItemStack stack, int x, int y) {
        super.renderToolTip(stack, x, y);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
