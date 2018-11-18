package xieao.theora.client.renderer.blockstate;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.common.block.TheoraBlocks;
import xieao.theora.common.block.misc.BlockShroom;
import xieao.theora.common.block.misc.BlockWood;

@SideOnly(Side.CLIENT)
public class TheoraStateMapper {

    public static void register() {
        ModelLoader.setCustomStateMapper(TheoraBlocks.SHROOM, new CustomStateMap.Builder().withName(BlockShroom.TYPE).withPreffix("shroom").build());
        ModelLoader.setCustomStateMapper(TheoraBlocks.WOOD, new CustomStateMap.Builder().withName(BlockWood.TYPE).withPreffix("wood").build());
    }

}
