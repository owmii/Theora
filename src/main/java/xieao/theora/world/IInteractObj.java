package xieao.theora.world;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xieao.theora.block.base.Tile;
import xieao.theora.core.lib.util.Empty;

public interface IInteractObj extends IInteractionObject {
    @Override
    default String getGuiID() {
        if (this instanceof Tile) {
            return ((Tile) this).getRegistryName().toString();
        }
        return Empty.STRING;
    }

    @OnlyIn(Dist.CLIENT)
    GuiScreen getGui();
}
