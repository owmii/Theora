package xieao.theora.block.hor;

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
import xieao.theora.item.ItemBlockBase;
import xieao.theora.item.ItemHor;
import xieao.theora.item.ItemPowder;

import javax.annotation.Nullable;

public class BlockHor extends BlockBase {
    public BlockHor(Properties properties) {
        super(properties);
    }

    @Override
    public ItemBlockBase getItemBlock(Item.Properties properties) {
        return new ItemHor(this, properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return new TileHor();
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
        for (int[] off : ItemPowder.OFFSETS_0) {
            for (int j = 0; j >= -3; j--) {
                BlockPos pos1 = pos.add(off[0], j, off[1]);
                worldIn.setBlockState(pos1, Blocks.AIR.getDefaultState(), 35);
                worldIn.playEvent(player, 2001, pos1, Block.getStateId(state));
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
