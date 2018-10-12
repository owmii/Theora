package xieao.theora.common.block.fermentingjar;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xieao.theora.common.block.BlockBase;

import javax.annotation.Nullable;

public class BlockFermentingJar extends BlockBase implements ITileEntityProvider {

    public BlockFermentingJar() {
        super(Material.ROCK);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileFermentingJar();
    }
}
