package xieao.theora.api.recipe.bindingstone;

import net.minecraft.item.ItemStack;
import xieao.theora.api.player.ability.Ability;
import xieao.theora.api.recipe.IRecipeRegistry;

public interface IBindingStoneRegistry extends IRecipeRegistry<IBindingStoneRecipe> {

    void addRecipe(Ability resultAbility, float liquidAmount, ItemStack... inputs);
}
