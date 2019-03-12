package xieao.theora.block.gate;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import xieao.theora.block.BlockBase;

import javax.annotation.Nullable;

public class BlockGatePart extends BlockBase {
    public BlockGatePart(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileGatePart) {
            TileGatePart gatePart = (TileGatePart) tileEntity;
            BlockPos pos1 = gatePart.getGatePos();
            if (pos1 != null) {
                IBlockState state1 = world.getBlockState(pos1);
                return state1.getBlock().onBlockActivated(state1, world, pos1, player, hand, side, hitX, hitY, hitZ);
            }
        }
        return super.onBlockActivated(state, world, pos, player, hand, side, hitX, hitY, hitZ);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return new TileGatePart();
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileGatePart) {
            TileGatePart gatePart = (TileGatePart) tileEntity;
            BlockPos pos1 = gatePart.getGatePos();
            if (pos1 != null) {
                IBlockState state1 = world.getBlockState(pos1);
                world.setBlockState(pos1, Blocks.AIR.getDefaultState(), 35);
                world.playEvent(player, 2001, pos1, Block.getStateId(state));
                state1.getBlock().onBlockHarvested(world, pos1, state1, player);
            }
        }
        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
}
