package xieao.theora.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import xieao.theora.block.IBlockBase;
import xieao.theora.client.renderer.item.TEItemRenderer;

public class BlockItemBase extends BlockItem implements IItemBase {
    @SuppressWarnings("ConstantConditions")
    public BlockItemBase(Block block, Properties properties) {
        super(block, properties.setTEISR(() -> TEItemRenderer::new)
                .group(block instanceof IBlockBase && ((IBlockBase) block).hideGroup() ? null : MAIN));
    }
}
