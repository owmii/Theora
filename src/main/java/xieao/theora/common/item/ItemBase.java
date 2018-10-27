package xieao.theora.common.item;

import net.minecraft.item.Item;
import xieao.theora.client.renderer.item.ItemRenderer;

public class ItemBase extends Item implements IGenericItem {

    public ItemBase() {
        setTileEntityItemStackRenderer(
                new ItemRenderer()
        );
    }
}
