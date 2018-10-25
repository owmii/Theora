package xieao.theora.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import xieao.theora.client.render.item.ItemRenderer;

public class ItemBlockBase extends ItemBlock {

    public ItemBlockBase(Block block) {
        super(block);
        setTileEntityItemStackRenderer(
                new ItemRenderer()
        );
    }
}
