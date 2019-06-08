package xieao.theora.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import xieao.theora.client.renderer.item.TEItemRenderer;

public class ItemBlockBase extends BlockItem implements IItemBase {
    public ItemBlockBase(Block block, Properties properties) {
        super(block, properties.setTEISR(() -> TEItemRenderer::new));
    }
}
