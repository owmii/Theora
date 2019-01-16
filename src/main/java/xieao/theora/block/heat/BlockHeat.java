package xieao.theora.block.heat;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import xieao.theora.block.IBlock;
import xieao.theora.item.IItem;
import xieao.theora.item.ItemHeat;

import javax.annotation.Nullable;

public class BlockHeat extends IBlock.Base implements ITileEntityProvider {
    private final int maxAge;

    public BlockHeat(int maxAge) {
        super(Block.Builder.create(Material.ROCK)
                .hardnessAndResistance(2.0F, 5.0F));
        this.maxAge = maxAge;
    }

    @Override
    public IItem.Block getItemBlock(Item.Builder builder) {
        return new ItemHeat(this, builder);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, @Nullable EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        TileEntity tileentity = world.getTileEntity(pos);
        if (tileentity instanceof TileHeat) {
            TileHeat tileHeat = (TileHeat) tileentity;
            if (stack.getItem() instanceof ItemHeat) {
                ItemHeat heat = (ItemHeat) stack.getItem();
                tileHeat.setAge(heat.getAge(stack));
            }
        }
    }

    @Override
    public void onReplaced(IBlockState state, World world, BlockPos pos, IBlockState newState, boolean isMoving) {
        TileEntity tileentity = world.getTileEntity(pos);
        if (tileentity instanceof TileHeat) {
            TileHeat tileHeat = (TileHeat) tileentity;
            ItemStack stack = new ItemStack(this);
            if (stack.getItem() instanceof ItemHeat) {
                ItemHeat heat = (ItemHeat) stack.getItem();
                heat.setAge(stack, tileHeat.getAge());
            }
            spawnAsEntity(world, pos, stack);
        }
        super.onReplaced(state, world, pos, newState, isMoving);
    }

    @Override
    public void dropBlockAsItemWithChance(IBlockState state, World worldIn, BlockPos pos, float chancePerItem, int fortune) {}

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileHeat();
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public int getMaxAge() {
        return maxAge;
    }
}
