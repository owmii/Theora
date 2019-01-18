package xieao.theora.api.recipe.interact;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.IRecipeRegistry;

public interface IInteractRegistry extends IRecipeRegistry<IInteractRecipe> {
    default void addRecipe(ItemStack result, Object block, Liquid liquid, float amount) {
        addRecipe(new InteractRecipe(result, block, liquid, amount));
    }

    default void addRecipe(IBlockState result, Object block, Liquid liquid, float amount) {
        addRecipe(new InteractRecipe(result, block, liquid, amount));
    }

    @Override
    default void addRecipe(IInteractRecipe recipe) {
        getRecipes().add(recipe);
    }
}
