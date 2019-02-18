package xieao.theora.world;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IInteractObj extends IInteractionObject {
    @OnlyIn(Dist.CLIENT)
    GuiScreen getGui(EntityPlayer player);
}
