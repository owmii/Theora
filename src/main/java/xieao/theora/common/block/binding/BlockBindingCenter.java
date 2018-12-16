package xieao.theora.common.block.binding;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
import java.util.Random;

public class BlockBindingCenter extends BlockBase implements ITileEntityProvider, IWandable {

    private static final AxisAlignedBB BB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0001D, 1.0D);

    public BlockBindingCenter() {
        super(Material.CLOTH);
        setHardness(9999.0F);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BB;
    }

    @Override
    public boolean performWand(World world, BlockPos pos, EntityPlayer player, EnumHand hand, IWand wand, @Nullable EnumFacing facing) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileBindingCenter) {
            TileBindingCenter bindingStone = (TileBindingCenter) tileEntity;
            if (!bindingStone.ready && bindingStone.ability.isEmpty()) {
                bindingStone.startBinding = true;
                bindingStone.markDirtyAndSync();
                return true;
            }
        }
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileBindingCenter();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.AIR;
    }


    @Override
    public int quantityDropped(Random random) {
        return 0;
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
