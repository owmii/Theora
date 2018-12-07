package xieao.theora.api.player.ability;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AbilityGui extends GuiScreen {

    public Ability ability;

    public AbilityGui(Ability ability) {
        this.ability = ability;
    }
}
