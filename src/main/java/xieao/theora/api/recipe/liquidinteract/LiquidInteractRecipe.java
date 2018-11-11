package xieao.theora.api.recipe.liquidinteract;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.api.liquid.Liquid;

public class LiquidInteractRecipe implements ILiquidInteractRecipe {

    private final IBlockState outState;
    private final Liquid liquid;
    private final float liquidAmount;
    private final IBlockState inState;
    private final boolean exactState;

    public LiquidInteractRecipe(IBlockState outState, Liquid liquid, float liquidAmount, IBlockState inState, boolean exactState) {
        this.outState = outState;
        this.liquid = liquid;
        this.liquidAmount = liquidAmount;
        this.inState = inState;
        this.exactState = exactState;
    }

    @Override
    public boolean matches(Liquid liquid, float liquidAmount, World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        boolean b0 = liquid.equals(getLiquid());
        boolean b1 = state.equals(getInState());
        boolean b2 = state.getBlock().equals(getInState().getBlock());
        return liquidAmount >= this.liquidAmount && b0 && (exactState() ? b1 : b2);
    }

    @Override
    public Liquid getLiquid() {
        return liquid;
    }

    @Override
    public float getLiquidAmount() {
        return liquidAmount;
    }

    @Override
    public IBlockState getInState() {
        return inState;
    }

    @Override
    public IBlockState getOutState() {
        return outState;
    }

    @Override
    public boolean exactState() {
        return exactState;
    }
}
