package xieao.theora.api.recipe;

import java.util.Set;

public interface IRecipeRegistry<T extends IRecipeRegistry.Recipe> {

    void initRecipes();

    Set<T> getRecipes();

    void addRecipe(T recipe);

    interface Recipe {

    }
}
