package xieao.theora.core.recipe;

import xieao.theora.api.recipe.interact.IInteractRecipe;
import xieao.theora.api.recipe.interact.IInteractRegistry;

import java.util.HashSet;
import java.util.Set;

public class InteractRecipes implements IInteractRegistry {
    private final Set<IInteractRecipe> recipes = new HashSet<>();

    @Override
    public void initRecipes() {

    }

    @Override
    public Set<IInteractRecipe> getRecipes() {
        return recipes;
    }
}
