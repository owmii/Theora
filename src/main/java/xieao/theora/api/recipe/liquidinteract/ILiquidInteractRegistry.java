package xieao.theora.api.recipe.liquidinteract;

import net.minecraft.block.state.IBlockState;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.IRecipeRegistry;

public interface ILiquidInteractRegistry extends IRecipeRegistry<ILiquidInteractRecipe> {

    void addRecipe(IBlockState outState, Liquid liquid, float liquidAmount, IBlockState inState, boolean exactState);

}
