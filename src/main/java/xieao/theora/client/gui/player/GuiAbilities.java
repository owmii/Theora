package xieao.theora.client.gui.player;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.ability.Abilities;
import xieao.theora.api.player.data.PlayerData;

@SideOnly(Side.CLIENT)
public class GuiAbilities extends GuiScreen {

    private final EntityPlayer player;
    private Abilities abilities;

    private int x, y, width, hiegh;

    public GuiAbilities(EntityPlayer player) {
        this.player = player;
        this.abilities = new Abilities();
        PlayerData data = TheoraAPI.getPlayerData(player);
        if (data != null) {
            this.abilities = data.getAbilities();
        }
    }

    @Override
    public void initGui() {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
