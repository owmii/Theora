package xieao.theora.client.renderer.blockstate;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.lib.render.blockstate.StateMapper;
import xieao.theora.common.block.TheoraBlocks;
import xieao.theora.common.block.misc.BlockKnowledgeStone;
import xieao.theora.common.block.misc.BlockMineral;
import xieao.theora.common.block.misc.BlockShroom;
import xieao.theora.common.block.misc.BlockWood;

@SideOnly(Side.CLIENT)
public class TheoraStateMapper {

    public static void register() {
        ModelLoader.setCustomStateMapper(TheoraBlocks.SHROOM, new StateMapper.Builder().withName(BlockShroom.TYPE).withPreffix("shroom").build());
        ModelLoader.setCustomStateMapper(TheoraBlocks.WOOD, new StateMapper.Builder().withName(BlockWood.TYPE).withPreffix("wood").build());
        ModelLoader.setCustomStateMapper(TheoraBlocks.MINERAL, new StateMapper.Builder().withName(BlockMineral.TYPE).withPreffix("mineral").build());
        ModelLoader.setCustomStateMapper(TheoraBlocks.KNOWLEDGE_STONE, new StateMapper.Builder().withName(BlockKnowledgeStone.TYPE).withPreffix("knowledgestone").build());
    }

}
