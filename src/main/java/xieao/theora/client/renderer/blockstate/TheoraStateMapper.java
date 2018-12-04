package xieao.theora.client.renderer.blockstate;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.common.block.TheoraBlocks;
import xieao.theora.common.block.misc.BlockKnowledgeStone;
import xieao.theora.common.block.misc.BlockMineral;
import xieao.theora.common.block.misc.BlockShroom;
import xieao.theora.common.block.misc.BlockWood;

@SideOnly(Side.CLIENT)
public class TheoraStateMapper {

    public static void register() {
        ModelLoader.setCustomStateMapper(TheoraBlocks.SHROOM, new CustomStateMap.Builder().withName(BlockShroom.TYPE).withPreffix("shroom").build());
        ModelLoader.setCustomStateMapper(TheoraBlocks.WOOD, new CustomStateMap.Builder().withName(BlockWood.TYPE).withPreffix("wood").build());
        ModelLoader.setCustomStateMapper(TheoraBlocks.MINERAL, new CustomStateMap.Builder().withName(BlockMineral.TYPE).withPreffix("mineral").build());
        ModelLoader.setCustomStateMapper(TheoraBlocks.KNOWLEDGE_STONE, new CustomStateMap.Builder().withName(BlockKnowledgeStone.TYPE).withPreffix("knowledgestone").build());
    }

}
