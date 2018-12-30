package xieao.theora.common.block.misc;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.lib.item.IGenericItem;
import xieao.lib.item.ItemBlockBase;
import xieao.theora.common.block.BlockPlant;
import xieao.theora.common.item.ItemShroom;
import xieao.theora.common.item.TheoraItems;

import java.util.List;
import java.util.Random;

public class BlockShroom extends BlockPlant implements IShearable {

    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (Type type : Type.values()) {
            items.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return TheoraItems.SHROOM_BIT;
    }

    public int quantityDropped(Random random) {
        return 1 + random.nextInt(4);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, Type.values()[meta]);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemBlockBase & IGenericItem> T getItemBlock() {
        return (T) new ItemShroom(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this, 1, world.getBlockState(pos).getValue(TYPE).ordinal()));
    }

    public enum Type implements IStringSerializable {
        GLIOPHORUS(762),
        WHITE_BEECH(541),
        WITCH_HAT(109),
        BLUE_HORN(278);

        public final int weight;

        Type(int weight) {
            this.weight = weight;
        }

        @Override
        public String getName() {
            return toString().toLowerCase();
        }
    }
}
