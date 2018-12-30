package xieao.theora.common.block.deathchamber;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import xieao.lib.block.BlockBase;

import javax.annotation.Nullable;

public class BlockDeathChamberWall extends BlockBase implements ITileEntityProvider {

    public BlockDeathChamberWall() {
        super(Material.ROCK);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileDeathChamberWall) {
            TileDeathChamberWall wall = (TileDeathChamberWall) tileEntity;
            if (wall.getBuilder() != null && facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
                double opX = facing.getOpposite().getFrontOffsetX();
                double opY = facing.getOpposite().getFrontOffsetY() - 1;
                double opZ = facing.getOpposite().getFrontOffsetZ();
                BlockPos pos1 = pos.add(opX, opY, opZ);
                if (wall.builderPos.equals(pos1)) {
                    TileEntity tileEntity1 = worldIn.getTileEntity(pos1);
                    if (tileEntity1 instanceof TileDeathChamber) {
                        TileDeathChamber deathChamber = (TileDeathChamber) tileEntity1;
                        if (deathChamber.built) {
                            ItemStack heldStack = playerIn.getHeldItem(hand);
                            int slot = facing.ordinal() - 1;
                            if (!deathChamber.getStackInSlot(slot).isEmpty()) {
                                ItemHandlerHelper.giveItemToPlayer(playerIn, deathChamber.getStackInSlot(slot).copy());
                                deathChamber.setInventorySlotContents(slot, ItemStack.EMPTY);
                                deathChamber.markDirtyAndSync();
                                return true;
                            }
                            if (deathChamber.isItemValidForSlot(slot, heldStack)) {
                                ItemStack copy = heldStack.copy();
                                copy.setCount(1);
                                deathChamber.setInventorySlotContents(slot, copy);
                                deathChamber.markDirtyAndSync();
                                heldStack.shrink(1);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileDeathChamberWall) {
            TileDeathChamberWall wall = (TileDeathChamberWall) tileEntity;
            if (!worldIn.isRemote && !wall.builderPos.equals(BlockPos.ORIGIN)) {
                if (entityIn instanceof EntityItem) {
                    entityIn.setPosition(wall.builderPos.getX() + 0.5D, wall.builderPos.getY() - 0.3D, wall.builderPos.getZ() + 0.5D);
                } else if (entityIn instanceof EntityLivingBase && !(entityIn instanceof EntityPlayer)) {
                    entityIn.setInvisible(true);
                    entityIn.setDead();
                }
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
        return new TileDeathChamberWall();
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
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
}
