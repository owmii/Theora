package xieao.theora.api.recipe.binding;

import net.minecraft.item.ItemStack;
import xieao.theora.api.player.ability.Ability;
import xieao.theora.api.recipe.IRecipeRegistry;

public interface IBindingRegistry extends IRecipeRegistry<IBindingRecipe> {

    void addRecipe(Ability resultAbility, float liquidAmount, ItemStack... inputs);
}
