package xieao.theora.block.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.INameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeBlock;
import xieao.theora.item.IItem;

import javax.annotation.Nullable;

public interface IBlock extends IForgeBlock {
    default IItem.Block getItemBlock(Item.Builder builder) {
        return new IItem.Block(getBlock(), builder);
    }

    class Plant extends BlockBush implements IBlock {
        public Plant() {
            this(Block.Builder.create(Material.PLANTS)
                    .doesNotBlockMovement()
                    .sound(SoundType.PLANT));
        }

        public Plant(Builder builder) {
            super(builder);
        }
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
                if (placer instanceof EntityPlayer) {
                    tile.setPlacerID(placer.getUniqueID());
                    tile.setFacing(placer.getHorizontalFacing().getOpposite());
                }
            }
        }

        @Override
        public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tileEntity, ItemStack stack) {
            if (tileEntity instanceof INameable && ((INameable) tileEntity).hasCustomName()) {
                player.addStat(StatList.BLOCK_MINED.get(this));
                player.addExhaustion(0.005F);
                if (world.isRemote) {
                    return;
                }
                int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
                Item item = getItemDropped(state, world, pos, enchantmentLevel).asItem();
                if (item == Items.AIR) {
                    return;
                }
                ItemStack itemStack = new ItemStack(item, quantityDropped(state, world.rand));
                itemStack.setDisplayName(((INameable) tileEntity).getCustomName());
                spawnAsEntity(world, pos, itemStack);
            } else {
                super.harvestBlock(world, player, pos, state, null, stack);
            }
        }

        @Override
        public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
            super.eventReceived(state, world, pos, id, param);
            TileEntity tileEntity = world.getTileEntity(pos);
            return tileEntity != null && tileEntity.receiveClientEvent(id, param);
        }
    }
}
