package xieao.theora.block.base;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeBlock;
import xieao.theora.core.lib.util.PlayerUtil;
import xieao.theora.item.IItem;

import javax.annotation.Nullable;

public interface IBlock extends IForgeBlock {
    default IItem.Block getItemBlock(Item.Builder builder) {
        return new IItem.Block(getBlock(), builder);
    }

    @Override
    default boolean hasTileEntity(IBlockState state) {
        return createTileEntity(state, null) != null;
    }

    class Base extends Block implements IBlock {
        public Base(Builder builder) {
            super(builder);
        }

        @Override
        public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, @Nullable EntityLivingBase placer, ItemStack stack) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof Tile) {
                Tile tile = (Tile) tileentity;
                if (stack.hasDisplayName()) {
                    tile.setCustomName(stack.getDisplayName());
                }
                if (placer instanceof EntityPlayer && !PlayerUtil.isFake((EntityPlayer) placer)) {
                    tile.setPlacerID(placer.getUniqueID());
                    tile.setFacing(placer.getHorizontalFacing().getOpposite());
                }
                tile.readStorable(stack.getTag() != null ? stack.getTag() : new NBTTagCompound());
                tile.onAdded(placer, stack);
            }
        }

        @Override
        public void onEntityCollision(IBlockState state, World worldIn, BlockPos pos, Entity entity) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof Tile) {
                ((Tile) tileentity).onCollision(entity);
            }
        }

        @Override
        public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof Tile) {
                return ((Tile) tileentity).interact(player, hand, side, hitX, hitY, hitZ);
            }
            return super.onBlockActivated(state, worldIn, pos, player, hand, side, hitX, hitY, hitZ);
        }

        @Override
        public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tileEntity, ItemStack stack) {
            if (tileEntity instanceof Tile) {
                player.addStat(StatList.BLOCK_MINED.get(this));
                player.addExhaustion(0.005F);
                Tile tile = (Tile) tileEntity;
                NBTTagCompound stackNBT = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();
                NBTTagCompound storableNBT = tile.writeStorable(stackNBT);
                if (storableNBT != null) {
                    stack.setTag(storableNBT);
                }
                stack.setDisplayName(tile.customName);
                spawnAsEntity(world, pos, stack);
            } else {
                super.harvestBlock(world, player, pos, state, null, stack);
            }
        }

        @Override
        public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
            TileEntity tileEntity = world.getTileEntity(pos);
            return tileEntity != null && tileEntity.receiveClientEvent(id, param);
        }
    }
}
