package xieao.theora.common.item;

import net.minecraft.block.Block;
import xieao.lib.item.ItemBlockBase;
import xieao.theora.common.block.misc.BlockMineral;

public class ItemMineral extends ItemBlockBase {

    public ItemMineral(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public Enum<?>[] getSubTypes() {
        return BlockMineral.Type.values();
    }
}
