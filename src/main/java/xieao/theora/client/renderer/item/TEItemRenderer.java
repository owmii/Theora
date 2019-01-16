package xieao.theora.client.renderer.item;

import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xieao.theora.item.IItem;

import java.util.HashSet;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class TEItemRenderer extends TileEntityItemStackRenderer {

    static final Set<Item> TE_ITEMS = new HashSet<>();

    @Override
    public void renderByItem(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof IItem && TE_ITEMS.contains(item)) {
            ((IItem) item).renderByItem(stack);
        }
    }

    public static void register(Item item) {
        if (item instanceof IItem) {
            IItem iItem = (IItem) item;
            if (iItem.renderByItem(new ItemStack(item))) {
                TE_ITEMS.add(item);
            }
        }
    }
}
