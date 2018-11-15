package xieao.theora.common.block.cauldron;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xieao.theora.common.block.BlockBase;

import javax.annotation.Nullable;

public class BlockCauldron extends BlockBase implements ITileEntityProvider {

    public BlockCauldron() {
        super(Material.ROCK);
        setSoundType(SoundType.METAL);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCauldron();
    }
}
