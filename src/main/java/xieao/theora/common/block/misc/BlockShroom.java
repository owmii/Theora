package xieao.theora.common.block.misc;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import xieao.theora.common.block.BlockPlant;
import xieao.theora.common.item.ItemShroom;

public class BlockShroom extends BlockPlant {

    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (Type type : Type.values()) {
            items.add(new ItemStack(this, 1, type.ordinal()));
        }
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
    public ItemBlock getItemBlock() {
        return new ItemShroom(this);
    }

    public enum Type implements IStringSerializable {
        WHITE_BEECH,
        GLIOPHORUS,
        WITCH_HAT,
        BLUE_HORN,


        ;

        @Override
        public String getName() {
            return toString().toLowerCase();
        }
    }
}
