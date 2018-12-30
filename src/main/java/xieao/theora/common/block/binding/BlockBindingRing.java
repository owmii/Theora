package xieao.theora.common.block.binding;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xieao.lib.block.BlockBase;

import javax.annotation.Nullable;

public class BlockBindingRing extends BlockBase implements ITileEntityProvider {

    private static final AxisAlignedBB BB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.02D, 1.0D);

    public BlockBindingRing() {
        super(Material.CLOTH);
        setSoundType(SoundType.CLOTH);
        setResistance(100.0F);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BB;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = playerIn.getHeldItem(hand);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileBindingRing) {
            TileBindingRing bindingRing = (TileBindingRing) tileEntity;
            if (!bindingRing.insertStack(held, 1, facing)) {
                return bindingRing.takeStack(playerIn, facing);
            }
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileBindingRing) {
            TileBindingRing bindingRing = (TileBindingRing) tileEntity;
            bindingRing.tryToBuild();
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileBindingRing) {
            TileBindingRing bindingRing = (TileBindingRing) tileEntity;
            bindingRing.tryToDemolish();
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileBindingRing();
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
