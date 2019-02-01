package xieao.theora.client.renderer.item;

import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IBlockColorHolder {
    @OnlyIn(Dist.CLIENT)
    IBlockColor getBlockColor();
}
