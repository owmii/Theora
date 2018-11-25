package xieao.theora.common.lib.multiblock;

import net.minecraft.util.math.BlockPos;
import xieao.theora.common.block.TileBase;

import javax.annotation.Nullable;

public interface IMultiBlockPart {

    boolean isLinked();

    void setLinked(boolean linked);

    @Nullable
    BlockPos getBuilderPos();

    void setBuilderPos(@Nullable BlockPos pos);

    @Nullable
    <T extends TileBase & IMultiBlockBuilder> T getBuilder();
}
