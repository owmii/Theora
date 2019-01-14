package xieao.theora.api.recipe.interact;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.api.liquid.Liquid;

public class InteractRecipe implements IInteractRecipe {
    private final Object result;
    private final Object block;
    private final Liquid liquid;
    private final float amount;

    public InteractRecipe(Object result, Object block, Liquid liquid, float amount) {
        this.result = result;
        this.block = block;
        this.liquid = liquid;
        this.amount = amount;
    }

    @Override
    public boolean matches(Liquid liquid, float amount, World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        boolean flag = false;
        if (this.block instanceof Tag<?>) {
            Tag<Block> tag = BlockTags.getCollection().get(((Tag<?>) this.block).getId());
            flag = tag != null && tag.contains(state.getBlock());
        } else if (this.block instanceof Block) {
            flag = state.getBlock().equals(((Block) this.block).getBlock());
        } else if (this.block instanceof IBlockState) {
            flag = state.equals(this.block);
        }
        return flag && liquid.equals(getLiquid()) && amount >= this.amount;
    }

    @Override
    public Object getResult() {
        return result;
    }

    @Override
    public Object getBlock() {
        return block;
    }

    @Override
    public Liquid getLiquid() {
        return liquid;
    }

    @Override
    public float getAmount() {
        return amount;
    }
}
