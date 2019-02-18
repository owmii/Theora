package xieao.theora.block.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
import net.minecraftforge.fml.network.NetworkHooks;
import xieao.theora.core.lib.util.PlayerUtil;
import xieao.theora.item.IItem;
import xieao.theora.world.IInteractObj;

import javax.annotation.Nullable;

public interface IBlock extends IForgeBlock {
    default IItem.Block getItemBlock(Item.Properties properties) {
        return new IItem.Block(getBlock(), properties);
    }

    @Override
    default boolean hasTileEntity(IBlockState state) {
        return createTileEntity(state, null) != null;
    }

    class Generic extends Block implements IBlock {
        public Generic(Properties properties) {
            super(properties);
        }

        @Override
        public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof Tile) {
                Tile tile = (Tile) tileentity;
                if (tile instanceof IInteractObj) {
                    IInteractObj io = (IInteractObj) tile;
                    if (!PlayerUtil.isFake(player) && player instanceof EntityPlayerMP) {
                        NetworkHooks.openGui((EntityPlayerMP) player, io, packetBuffer -> packetBuffer.writeBlockPos(pos));
                    }
                    return true;
                }
                return tile.interact(player, hand, side, hitX, hitY, hitZ);
            }
            return super.onBlockActivated(state, worldIn, pos, player, hand, side, hitX, hitY, hitZ);
        }

        @Override
        public void onEntityCollision(IBlockState state, World worldIn, BlockPos pos, Entity entity) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof Tile) {
                ((Tile) tileentity).onCollision(entity);
            }
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
                NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();
                tile.readStorable(tag.getCompound("TileStorableNBT"));
                tile.onAdded(placer, stack);
            }
        }

        @Override
        public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tileEntity, ItemStack stack) {
            if (tileEntity instanceof Tile) {
                Tile tile = (Tile) tileEntity;
                ItemStack stack1 = new ItemStack(this);
                NBTTagCompound tag = stack1.getTag() != null ? stack1.getTag() : new NBTTagCompound();
                NBTTagCompound storable = tile.writeStorable(new NBTTagCompound());
                if (!storable.isEmpty()) {
                    tag.setTag("TileStorableNBT", storable);
                    stack1.setTag(tag);
                }
                if (tile.customName != null) {
                    stack1.setDisplayName(tile.customName);
                }
                spawnAsEntity(world, pos, stack1);
                player.addStat(StatList.BLOCK_MINED.get(this));
                player.addExhaustion(0.005F);
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

    class Plant extends BlockBush implements IBlock {
        public Plant(Properties properties) {
            super(properties);
        }

        public Plant() {
            this(Properties.create(Material.PLANTS)
                    .doesNotBlockMovement()
                    .sound(SoundType.PLANT));
        }
    }
}
