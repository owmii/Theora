package xieao.theora.common.item;

import net.minecraft.block.Block;
import xieao.theora.common.block.misc.BlockShroom;

public class ItemShroom extends ItemBlockBase {

    public ItemShroom(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public Enum<?>[] getSubTypeValues() {
        return BlockShroom.Type.values();
    }
}
