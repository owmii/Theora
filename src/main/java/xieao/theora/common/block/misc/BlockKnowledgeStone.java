package xieao.theora.common.block.misc;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import xieao.theora.common.block.BlockBase;
import xieao.theora.common.item.IGenericItem;
import xieao.theora.common.item.ItemBlockBase;
import xieao.theora.common.item.ItemKnowledgeStone;

public class BlockKnowledgeStone extends BlockBase {

    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    public BlockKnowledgeStone() {
        super(Material.ROCK);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemBlockBase & IGenericItem> T getItemBlock() {
        return (T) new ItemKnowledgeStone(this);
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

    public float getKnowledge(IBlockState state) {
        return state.getValue(TYPE).knowledge;
    }

    public enum Type implements IStringSerializable {
        PRIMA(0.57F),
        CAPTIOSUS(1.74F),
        ACUMEN(4.92F),
        ALTUM(9.38F);

        public final float knowledge;

        Type(float knowledge) {
            this.knowledge = knowledge;
        }

        @Override
        public String getName() {
            return toString().toLowerCase();
        }
    }
}
