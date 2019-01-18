package xieao.theora.core.recipe;

import xieao.theora.api.recipe.cauldron.ICauldronRecipe;
import xieao.theora.api.recipe.cauldron.ICauldronRegistry;

import java.util.HashSet;
import java.util.Set;

public class CauldronRecipes implements ICauldronRegistry {
    private final Set<ICauldronRecipe> recipes = new HashSet<>();

    @Override
    public void initRecipes() {

    }

    @Override
    public Set<ICauldronRecipe> getRecipes() {
        return recipes;
    }
}
