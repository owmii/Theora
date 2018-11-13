package xieao.theora.common.block.misc;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import xieao.theora.common.block.BlockPlant;
import xieao.theora.common.item.IGenericItem;
import xieao.theora.common.item.ItemBlockBase;
import xieao.theora.common.item.ItemShroom;
import xieao.theora.common.item.TheoraItems;

import java.util.Random;

public class BlockShroom extends BlockPlant {

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

    public enum Type implements IStringSerializable {
        WHITE_BEECH(19),
        GLIOPHORUS(24),
        WITCH_HAT(7),
        BLUE_HORN(16),
        ;

        private final int weight;

        Type(int weight) {
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String getName() {
            return toString().toLowerCase();
        }
    }
}
