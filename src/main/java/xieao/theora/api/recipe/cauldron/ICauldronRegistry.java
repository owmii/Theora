package xieao.theora.api.recipe.cauldron;

import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.IRecipeRegistry;

public interface ICauldronRegistry extends IRecipeRegistry<ICauldronRecipe> {
    default void addRecipe(Liquid liquid, Object... inputs) {
        addRecipe(new CauldronRecipe(liquid, inputs));
    }

    @Override
    default void addRecipe(ICauldronRecipe recipe) {
        getRecipes().add(recipe);
    }
}
