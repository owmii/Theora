package xieao.theora.common.block.deathchamber;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.common.block.BlockBase;

import javax.annotation.Nullable;

public class BlockDeathChamberWall extends BlockBase implements ITileEntityProvider {

    public BlockDeathChamberWall() {
        super(Material.ROCK);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileDeathChamberWall();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileDeathChamberWall) {
            TileEntity tileEntity1 = worldIn.getTileEntity(((TileDeathChamberWall) tileEntity).dcPos);
            if (tileEntity1 instanceof TileDeathChamber) {
                ((TileDeathChamber) tileEntity1).dimolish();
                ((TileDeathChamber) tileEntity1).syncNBTData();
            }
        }
        super.breakBlock(worldIn, pos, state);
    }
}
