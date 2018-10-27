package xieao.theora.api.recipe.fermentingjar;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.IRecipeRegistry;

public interface IFermentingRecipe extends IRecipeRegistry.Entry {

    boolean matches(ItemStack stack, World world, BlockPos pos);

    ItemStack getInputStack();

    ItemStack getOutputStack();

    Liquid getOutputLiquid();
}
