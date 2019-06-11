package xieao.theora.item;

import net.minecraft.item.Item;
import xieao.theora.client.renderer.item.TEItemRenderer;

public class ItemBase extends Item implements IItemBase {
    public ItemBase(Properties properties) {
        super(properties.setTEISR(() -> TEItemRenderer::new).group(MAIN));
    }
}
