package xieao.theora.common.block.cauldron;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import xieao.theora.api.item.wand.IWand;
import xieao.theora.api.item.wand.IWandable;
import xieao.theora.common.block.BlockBase;
import xieao.theora.common.block.IHeatedBlock;
import xieao.theora.common.block.TheoraBlocks;
import xieao.theora.common.item.IGenericItem;
import xieao.theora.common.item.ItemBlockBase;
import xieao.theora.common.item.ItemCauldron;

import javax.annotation.Nullable;

public class BlockCauldron extends BlockBase implements IHeatedBlock, IWandable, ITileEntityProvider {

    public BlockCauldron() {
        super(Material.ROCK);
        setSoundType(SoundType.METAL);
        setResistance(20.0F);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ItemBlockBase & IGenericItem> T getItemBlock() {
        return (T) new ItemCauldron(this);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.8D, 0.9D);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        ItemStack held = playerIn.getHeldItem(hand);
        if (tileEntity instanceof TileCauldron) {
            TileCauldron cauldron = (TileCauldron) tileEntity;
            boolean flag = false;
            if (!worldIn.isRemote) {
                FluidActionResult fluidActionResult = FluidUtil.tryEmptyContainer(held, cauldron.fluidTank, Fluid.BUCKET_VOLUME, playerIn, true);
                ItemStack stack = fluidActionResult.getResult();
                if (!stack.isEmpty()) {
                    playerIn.setHeldItem(hand, stack);
                    flag = true;
                } else {
                    FluidActionResult fluidActionResult1 = FluidUtil.tryFillContainer(held, cauldron.fluidTank, Fluid.BUCKET_VOLUME, playerIn, true);
                    ItemStack stack1 = fluidActionResult1.getResult();
                    if (!stack1.isEmpty()) {
                        playerIn.setHeldItem(hand, stack1);
                        InventoryHelper.dropInventoryItems(worldIn, pos, cauldron);
                        flag = true;
                    }
                }
                cauldron.syncNBTData();
            }
            if (!flag) {
                cauldron.takeStack(playerIn, facing);
            }
            return true;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean performWand(World world, BlockPos pos, EntityPlayer player, EnumHand hand, IWand wand, @Nullable EnumFacing facing) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileCauldron) {
            TileCauldron cauldron = (TileCauldron) tileEntity;
            cauldron.started = true;
            cauldron.syncNBTData();
            return true;
        }
        return false;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileCauldron) {
            TileCauldron cauldron = (TileCauldron) tileEntity;
            if (entityIn instanceof EntityItem) {
                cauldron.collectItems((EntityItem) entityIn, 1);
            }
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCauldron();
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileCauldron) {
            TileCauldron cauldron = (TileCauldron) tileEntity;
            if (heated(world, pos)) {
                world.setBlockState(pos, TheoraBlocks.EMBER.getDefaultState(), 2);
            }
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean heated(World world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileCauldron) {
            TileCauldron cauldron = (TileCauldron) tileEntity;
            return cauldron.heated;
        }
        return false;
    }

    @Override
    public void setHeated(World world, BlockPos pos, IBlockState heat) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileCauldron) {
            TileCauldron cauldron = (TileCauldron) tileEntity;
            cauldron.heated = true;
            cauldron.syncNBTData();
        }
    }
}
