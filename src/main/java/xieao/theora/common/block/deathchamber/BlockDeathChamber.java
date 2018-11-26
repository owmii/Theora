package xieao.theora.common.block.deathchamber;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import xieao.theora.api.item.slate.ISummoningSlate;
import xieao.theora.common.block.BlockBase;

import javax.annotation.Nullable;

public class BlockDeathChamber extends BlockBase implements ITileEntityProvider {

    public BlockDeathChamber() {
        super(Material.ROCK);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9D, 1.0D);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileDeathChamber) {
            TileDeathChamber deathChamber = (TileDeathChamber) tileEntity;
            if (deathChamber.built && facing == EnumFacing.DOWN) {
                ItemStack heldStack = playerIn.getHeldItem(hand);
                if (!deathChamber.getStackInSlot(0).isEmpty()) {
                    ItemHandlerHelper.giveItemToPlayer(playerIn, deathChamber.getStackInSlot(0).copy());
                    deathChamber.setInventorySlotContents(0, ItemStack.EMPTY);
                    deathChamber.syncNBTData();
                    return true;
                }
                if (deathChamber.getStackInSlot(0).isEmpty() && heldStack.getItem() instanceof ISummoningSlate) {
                    ItemStack copy = heldStack.copy();
                    copy.setCount(1);
                    deathChamber.setInventorySlotContents(0, copy);
                    deathChamber.syncNBTData();
                    heldStack.shrink(1);
                    return true;
                }
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
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
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!worldIn.isRemote) {
            if (entityIn instanceof EntityItem) {
                entityIn.setPosition(pos.getX() + 0.5D, pos.getY() - 0.3D, pos.getZ() + 0.5D);
            } else if (entityIn instanceof EntityLivingBase && !(entityIn instanceof EntityPlayer)) {
                entityIn.setInvisible(true);
                entityIn.setDead();
            }
        }
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileDeathChamber();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileDeathChamber) {
            TileDeathChamber chamber = (TileDeathChamber) tileEntity;
            InventoryHelper.dropInventoryItems(worldIn, pos, chamber);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
}
