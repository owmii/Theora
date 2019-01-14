package xieao.theora.core.recipe;

import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.cauldron.CauldronRecipe;
import xieao.theora.api.recipe.cauldron.ICauldronRecipe;
import xieao.theora.api.recipe.cauldron.ICauldronRegistry;

import java.util.HashSet;
import java.util.Set;

public class CauldronRecipes implements ICauldronRegistry {
    final Set<ICauldronRecipe> recipes = new HashSet<>();

    @Override
    public void initRecipes() {

    }

    @Override
    public void addRecipe(Liquid liquid, Object... inputs) {
        addRecipe(new CauldronRecipe(liquid, inputs));
    }

    @Override
    public void addRecipe(ICauldronRecipe recipe) {
        this.recipes.add(recipe);
    }

    @Override
    public Set<ICauldronRecipe> getRecipes() {
        return recipes;
    }
}
