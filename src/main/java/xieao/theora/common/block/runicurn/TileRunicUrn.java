package xieao.theora.common.block.runicurn;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.api.liquid.LiquidSlot;
import xieao.theora.common.block.TileLiquidContainer;
import xieao.theora.common.block.misc.BlockKnowledgeStone;
import xieao.theora.common.lib.config.ConfigMain;
import xieao.theora.common.liquid.TheoraLiquids;

public class TileRunicUrn extends TileLiquidContainer implements ITickable {

    public TileRunicUrn() {
        this.liquidContainer.addLiquidSlots(
                new LiquidSlot(TheoraLiquids.LAOWM, true, 1000.0F, 0.0F, 100.0F, LiquidSlot.TransferType.SEND)
        );
    }

    public LiquidSlot getLiquidSlot() {
        return this.liquidContainer.getLiquidSlot(0);
    }

    @Override
    public void update() {
        if (getWorld().getTotalWorldTime() % 10 == 0) {
            float knowledgePower = 0;
            for (int j = -1; j <= 1; ++j) {
                for (int k = -1; k <= 1; ++k) {
                    if ((j != 0 || k != 0) && getWorld().isAirBlock(getPos().add(k, 0, j)) && getWorld().isAirBlock(getPos().add(k, 1, j))) {
                        knowledgePower += getKnowledgePower(getWorld(), getPos().add(k * 2, 0, j * 2));
                        knowledgePower += getKnowledgePower(getWorld(), getPos().add(k * 2, 1, j * 2));
                        if (k != 0 && j != 0) {
                            knowledgePower += getKnowledgePower(getWorld(), getPos().add(k * 2, 0, j));
                            knowledgePower += getKnowledgePower(getWorld(), getPos().add(k * 2, 1, j));
                            knowledgePower += getKnowledgePower(getWorld(), getPos().add(k, 0, j * 2));
                            knowledgePower += getKnowledgePower(getWorld(), getPos().add(k, 1, j * 2));
                        }
                    }
                }
            }
            LiquidSlot liquidSlot = getLiquidSlot();
            float stored = liquidSlot.getStored();
            if (!liquidSlot.isFull()) {
                liquidSlot.setStored(stored + knowledgePower * ConfigMain.runicUrnBaseProduction);
            }
        }
    }

    public static float getKnowledgePower(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        return block instanceof BlockKnowledgeStone ? ((BlockKnowledgeStone) block).getKnowledge(state) : 0.0F;
    }

    @Override
    public boolean keepData() {
        return !getLiquidSlot().isEmpty();
    }
}
