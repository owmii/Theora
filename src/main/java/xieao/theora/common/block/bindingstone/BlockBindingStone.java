package xieao.theora.common.block.bindingstone;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xieao.theora.api.item.wand.IWand;
import xieao.theora.api.item.wand.IWandable;
import xieao.theora.common.block.BlockBase;

import javax.annotation.Nullable;

public class BlockBindingStone extends BlockBase implements ITileEntityProvider, IWandable {

    private static final AxisAlignedBB BB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D / 16.0D, 1.0D);

    public BlockBindingStone() {
        super(Material.ROCK);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BB;
    }

    @Override
    public boolean performWand(World world, BlockPos pos, EntityPlayer player, EnumHand hand, IWand wand, @Nullable EnumFacing facing) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileBindingStone) {
            TileBindingStone bindingStone = (TileBindingStone) tileEntity;
            if (!bindingStone.ready && bindingStone.ability.isEmpty()) {
                bindingStone.startBinding = true;
                bindingStone.syncNBTData();
                return true;
            }
        }
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileBindingStone();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
}
