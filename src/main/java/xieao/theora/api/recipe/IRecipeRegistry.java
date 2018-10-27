package xieao.theora.api.recipe;

import java.util.Set;

public interface IRecipeRegistry<T extends IRecipeRegistry.Entry> {

    void initRecipes();

    Set<T> getRecipes();

    void addRecipe(T recipe);

    interface Entry {

    }

}
