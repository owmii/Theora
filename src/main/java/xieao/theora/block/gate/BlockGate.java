package xieao.theora.block.gate;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import xieao.theora.block.BlockBase;
import xieao.theora.core.IBlocks;
import xieao.theora.item.ItemBlockBase;
import xieao.theora.item.ItemGate;

import javax.annotation.Nullable;

public class BlockGate extends BlockBase {
    public BlockGate(Properties properties) {
        super(properties);
    }

    @Override
    public ItemBlockBase getItemBlock(Item.Properties properties) {
        return new ItemGate(this, properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return new TileGate();
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isSolid(IBlockState state) {
        return false;
    }

    @Override
    public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
        return Items.AIR;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileGate) {
            TileGate gate = (TileGate) tileEntity;
            if (gate.isGateBase() && worldIn.getBlockState(pos.up()).getBlock() == IBlocks.GATE) {
                worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 35);
                worldIn.playEvent(player, 2001, pos.up(), Block.getStateId(state));
            } else if (worldIn.getBlockState(pos).getBlock() == IBlocks.GATE) {
                TileEntity tileEntity1 = worldIn.getTileEntity(pos.down());
                if (tileEntity1 instanceof TileGate) {
                    TileGate gate1 = (TileGate) tileEntity1;
                    if (gate1.isGateBase()) {
                        worldIn.setBlockState(pos.down(), Blocks.AIR.getDefaultState(), 35);
                        worldIn.playEvent(player, 2001, pos.down(), Block.getStateId(state));
                    }
                }
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
}
