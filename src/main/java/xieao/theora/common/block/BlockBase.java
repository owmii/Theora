package xieao.theora.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.tuple.Pair;
import xieao.theora.common.item.IBookItem;
import xieao.theora.common.item.IGenericItem;
import xieao.theora.common.item.ItemBase;
import xieao.theora.common.item.ItemBlockBase;
import xieao.theora.common.lib.book.Entry;
import xieao.theora.common.lib.helper.NBTHelper;
import xieao.theora.common.lib.multiblock.IMultiBlockBuilder;
import xieao.theora.common.lib.multiblock.IMultiBlockPart;

import javax.annotation.Nullable;
import java.util.Objects;

public abstract class BlockBase extends Block implements IGenericBlock, IBookBlock {

    public static final String TAG_TILE_DATA = "tileDataTag";

    public BlockBase(Material material) {
        super(material);
        setResistance(10.0F);
        setHardness(0.8F);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ItemBlockBase & IGenericItem> T getItemBlock() {
        return (T) new ItemBlockBase(this);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileBase) {
            TileBase tileBase = (TileBase) tileEntity;
            if (NBTHelper.hasNBT(stack) && NBTHelper.hasKey(stack, TAG_TILE_DATA, Constants.NBT.TAG_COMPOUND)) {
                tileBase.readNBT(NBTHelper.getCompoundTag(stack, TAG_TILE_DATA));
            }
            if (placer instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) placer;
                tileBase.setFacingAngle(player.getHorizontalFacing().getOpposite().getHorizontalAngle());
                tileBase.setPlacer(player.getUniqueID());
            }
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileBase) {
            TileBase tileBase = (TileBase) tileEntity;
            if (tileBase.isPowered()) {
                tileBase.onRedstonePulse();
            }
        }
    }

    protected boolean isInvalidNeighbor(World worldIn, BlockPos pos, EnumFacing facing) {
        return worldIn.getBlockState(pos.offset(facing)).getMaterial() == Material.CACTUS;
    }

    protected boolean hasInvalidNeighbor(World worldIn, BlockPos pos) {
        return isInvalidNeighbor(worldIn, pos, EnumFacing.NORTH)
                || isInvalidNeighbor(worldIn, pos, EnumFacing.SOUTH)
                || isInvalidNeighbor(worldIn, pos, EnumFacing.WEST)
                || isInvalidNeighbor(worldIn, pos, EnumFacing.EAST);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileBase) {
            TileBase tileBase = (TileBase) tileEntity;
            if (tileBase instanceof IMultiBlockBuilder) {
                IMultiBlockBuilder builder = (IMultiBlockBuilder) tileBase;
                builder.dimolish(tileBase);
            } else if (tileBase instanceof IMultiBlockPart) {
                IMultiBlockPart part = (IMultiBlockPart) tileEntity;
                IMultiBlockBuilder builder = part.getBuilder();
                if (builder != null) {
                    builder.dimolish(part.getBuilder());
                }
            }
        }
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (te instanceof IWorldNameable && ((IWorldNameable) te).hasCustomName()) {
            player.addStat(Objects.requireNonNull(StatList.getBlockStats(this)));
            player.addExhaustion(0.005F);
            if (world.isRemote) {
                return;
            }
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
            Item item = this.getItemDropped(state, world.rand, i);
            if (item == Items.AIR) {
                return;
            }
            ItemStack itemstack = new ItemStack(item, this.quantityDropped(world.rand));
            itemstack.setStackDisplayName(((IWorldNameable) te).getName());
            spawnAsEntity(world, pos, itemstack);
        } else {
            super.harvestBlock(world, player, pos, state, null, stack);
        }
        world.setBlockToAir(pos);
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, fortune);
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileBase) {
            TileBase tileBase = (TileBase) tileEntity;
            if (tileBase.keepData()) {
                NBTTagCompound compound = new NBTTagCompound();
                tileBase.writeNBT(compound);
                ItemStack stack = drops.get(0);
                NBTHelper.setTag(stack, TAG_TILE_DATA, compound);
            }
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (willHarvest) return true; //If it will harvest, delay deletion of the block until after getDrops
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }

    @Override
    public IBookItem getBookItem() {
        return getItemBlock();
    }

    @Nullable
    @Override
    public Pair<Entry, Integer> getBookPage() {
        return getBookItem().getBookPage();
    }

    @Override
    public <T extends ItemBase & IBookItem> T setBookPage(Entry entry, int index) {
        return getBookItem().setBookPage(entry, index);
    }
}
