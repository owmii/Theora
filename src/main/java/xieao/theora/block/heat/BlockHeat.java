package xieao.theora.block.heat;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import xieao.theora.block.base.IBlock;
import xieao.theora.item.IItem;
import xieao.theora.item.ItemHeat;

import javax.annotation.Nullable;

public class BlockHeat extends IBlock.Generic {
    private final int maxAge;

    public BlockHeat(int maxAge) {
        super(Block.Builder.create(Material.ROCK)
                .hardnessAndResistance(2.0F, 5.0F));
        this.maxAge = maxAge;
    }

    @Override
    public IItem.Block getItemBlock(Item.Builder builder) {
        return new ItemHeat(this, builder);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return new TileHeat();
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public int getMaxAge() {
        return maxAge;
    }
}
