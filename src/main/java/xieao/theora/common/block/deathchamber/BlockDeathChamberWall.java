package xieao.theora.common.block.deathchamber;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import xieao.theora.common.block.BlockBase;

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
            if (wall.hasDeathChamber() && facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
                double opX = facing.getOpposite().getFrontOffsetX();
                double opY = facing.getOpposite().getFrontOffsetY();
                double opZ = facing.getOpposite().getFrontOffsetZ();
                BlockPos pos1 = pos.add(opX, opY, opZ);
                if (wall.dcPos.equals(pos1)) {
                    TileEntity tileEntity1 = worldIn.getTileEntity(pos1);
                    if (tileEntity1 instanceof TileDeathChamber) {
                        TileDeathChamber deathChamber = (TileDeathChamber) tileEntity1;
                        if (deathChamber.buildStatus) {
                            ItemStack heldStack = playerIn.getHeldItem(hand);
                            int slot = facing.ordinal() - 1;
                            System.out.println(slot);
                            if (!deathChamber.getStackInSlot(slot).isEmpty()) {
                                ItemHandlerHelper.giveItemToPlayer(playerIn, deathChamber.getStackInSlot(slot).copy());
                                deathChamber.setInventorySlotContents(slot, ItemStack.EMPTY);
                                deathChamber.syncNBTData();
                                return true;
                            }
                            if (deathChamber.isItemValidForSlot(slot, heldStack)) {
                                ItemStack copy = heldStack.copy();
                                copy.setCount(1);
                                deathChamber.setInventorySlotContents(slot, copy);
                                deathChamber.syncNBTData();
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
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileDeathChamberWall) {
            TileEntity tileEntity1 = worldIn.getTileEntity(((TileDeathChamberWall) tileEntity).dcPos);
            if (tileEntity1 instanceof TileDeathChamber) {
                ((TileDeathChamber) tileEntity1).dimolish();
                ((TileDeathChamber) tileEntity1).syncNBTData();
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
}
