package xieao.theora.common.block.orb;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xieao.theora.common.block.BlockBase;

import javax.annotation.Nullable;

public class BlockOrb extends BlockBase implements ITileEntityProvider {

    public BlockOrb() {
        super(Material.CLOTH);
        setSoundType(SoundType.GLASS);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileOrb();
    }
}
