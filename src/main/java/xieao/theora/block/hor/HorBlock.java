package xieao.theora.block.hor;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import xieao.theora.block.BlockBase;

import javax.annotation.Nullable;

public class HorBlock extends BlockBase {
    public HorBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new HorTile();
    }
}
