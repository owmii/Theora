package xieao.theora.api.recipe.cauldron;

import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.IRecipeRegistry;

public interface ICauldronRegistry extends IRecipeRegistry<ICauldronRecipe> {

    void addRecipe(Liquid liquid, Object... objects);

}
