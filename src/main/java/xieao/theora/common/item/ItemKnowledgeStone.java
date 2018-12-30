package xieao.theora.common.item;

import net.minecraft.block.Block;
import xieao.theora.common.block.misc.BlockKnowledgeStone;

public class ItemKnowledgeStone extends ItemBlockBase {

    public ItemKnowledgeStone(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public Enum<?>[] getSubTypes() {
        return BlockKnowledgeStone.Type.values();
    }
}
