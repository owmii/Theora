package xieao.theora.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkHooks;
import xieao.theora.world.IInteractObj;

import javax.annotation.Nullable;

public class BlockBase extends Block implements IBlockBase {
    public BlockBase(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileBase) {
            TileBase tile = (TileBase) tileentity;
            if (tile instanceof IInteractObj) {
                if (player instanceof EntityPlayerMP && !(player instanceof FakePlayer)) {
                    NetworkHooks.openGui((EntityPlayerMP) player, (IInteractObj) tile, packetBuffer -> {
                        packetBuffer.writeString("tile.gui");
                        packetBuffer.writeBlockPos(pos);
                    });
                }
                return true;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, hand, side, hitX, hitY, hitZ);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, @Nullable EntityLivingBase placer, ItemStack stack) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileBase) {
            TileBase tile = (TileBase) tileentity;
            NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();
            tile.readStorable(tag.getCompound("TileStorableNBT"));
        }
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tileEntity, ItemStack stack) {
        if (tileEntity instanceof TileBase) {
            TileBase tile = (TileBase) tileEntity;
            ItemStack stack1 = new ItemStack(this);
            NBTTagCompound tag = stack1.getTag() != null ? stack1.getTag() : new NBTTagCompound();
            NBTTagCompound storable = tile.writeStorable(new NBTTagCompound());
            if (!storable.isEmpty()) {
                tag.put("TileStorableNBT", storable);
                stack1.setTag(tag);
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
