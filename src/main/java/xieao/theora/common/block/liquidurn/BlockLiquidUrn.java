package xieao.theora.common.block.liquidurn;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xieao.theora.common.block.BlockBase;

import javax.annotation.Nullable;

public class BlockLiquidUrn extends BlockBase implements ITileEntityProvider {

    public BlockLiquidUrn() {
        super(Material.WOOD);
        setSoundType(SoundType.WOOD);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileLiquidUrn();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
