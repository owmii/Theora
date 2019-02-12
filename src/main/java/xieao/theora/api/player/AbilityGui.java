package xieao.theora.api.player;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AbilityGui extends GuiScreen {

    public Ability ability;

    public AbilityGui(Ability ability) {
        this.ability = ability;
    }
}
