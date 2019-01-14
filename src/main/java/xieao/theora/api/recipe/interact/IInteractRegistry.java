package xieao.theora.api.recipe.interact;

import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.IRecipeRegistry;

public interface IInteractRegistry extends IRecipeRegistry<IInteractRecipe> {
    void addRecipe(Object result, Object block, Liquid liquid, float amount);
}
