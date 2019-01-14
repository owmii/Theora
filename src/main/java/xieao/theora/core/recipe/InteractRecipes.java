package xieao.theora.core.recipe;

import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.interact.IInteractRecipe;
import xieao.theora.api.recipe.interact.IInteractRegistry;
import xieao.theora.api.recipe.interact.InteractRecipe;

import java.util.HashSet;
import java.util.Set;

public class InteractRecipes implements IInteractRegistry {
    final Set<IInteractRecipe> recipes = new HashSet<>();

    @Override
    public void initRecipes() {

    }

    @Override
    public void addRecipe(Object result, Object block, Liquid liquid, float amount) {
        addRecipe(new InteractRecipe(result, block, liquid, amount));
    }

    @Override
    public void addRecipe(IInteractRecipe recipe) {
        this.recipes.add(recipe);
    }

    @Override
    public Set<IInteractRecipe> getRecipes() {
        return recipes;
    }
}
