package xieao.theora.api.recipe.liquidinteract;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.IRecipeRegistry;

public interface ILiquidInteractRecipe extends IRecipeRegistry.Entry {

    boolean matches(Liquid liquid, float liquidAmount, World world, BlockPos pos);

    Liquid getLiquid();

    float getLiquidAmount();

    IBlockState getInState();

    IBlockState getOutState();

    boolean exactState();
}
