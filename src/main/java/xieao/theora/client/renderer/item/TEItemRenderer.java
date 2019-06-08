package xieao.theora.client.renderer.item;

import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TEItemRenderer extends ItemStackTileEntityRenderer {

    @Override
    public void renderByItem(ItemStack stack) {
        final Item item = stack.getItem();

    }
}
