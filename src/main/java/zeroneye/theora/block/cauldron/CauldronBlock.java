package zeroneye.theora.block.cauldron;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import zeroneye.lib.block.BlockBase;

import javax.annotation.Nullable;

public class CauldronBlock extends BlockBase {
    public CauldronBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CauldronTile();
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
