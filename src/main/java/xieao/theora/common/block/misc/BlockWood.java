package xieao.theora.common.block.misc;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import xieao.lib.block.BlockBase;
import xieao.lib.item.IGenericItem;
import xieao.lib.item.ItemBlockBase;

public class BlockWood extends BlockBase {

    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    public BlockWood() {
        super(Material.WOOD);
        setSoundType(SoundType.WOOD);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemBlockBase & IGenericItem> T getItemBlock() {
        return (T) new ItemBlockBase(this, Type.values());
    }

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

    public enum Type implements IStringSerializable {
        LAVANA;

        @Override
        public String getName() {
            return toString().toLowerCase();
        }
    }
}
