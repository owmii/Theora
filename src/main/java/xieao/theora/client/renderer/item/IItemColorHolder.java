package xieao.theora.client.renderer.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IItemColorHolder {
    @OnlyIn(Dist.CLIENT)
    IItemColor getItemColor();
}
