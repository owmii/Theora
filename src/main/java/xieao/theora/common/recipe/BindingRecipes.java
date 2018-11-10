package xieao.theora.common.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import xieao.theora.api.player.ability.Ability;
import xieao.theora.api.recipe.binding.BindingRecipe;
import xieao.theora.api.recipe.binding.IBindingRecipe;
import xieao.theora.api.recipe.binding.IBindingRegistry;
import xieao.theora.common.ability.TheoraAbilities;

import java.util.HashSet;
import java.util.Set;

public class BindingRecipes implements IBindingRegistry {

    private final Set<IBindingRecipe> recipes = new HashSet<>();

    @Override
    public void initRecipes() {
        addRecipe(TheoraAbilities.ME_PIG, 0, new ItemStack(Items.CARROT), new ItemStack(Items.APPLE), new ItemStack(Items.CARROT), new ItemStack(Items.CARROT));
    }

    @Override
    public void addRecipe(Ability resultAbility, float liquidAmount, ItemStack... inputs) {
        this.recipes.add(new BindingRecipe(resultAbility, liquidAmount, inputs));
    }

    @Override
    public void addRecipe(IBindingRecipe recipe) {
        this.recipes.add(recipe);
    }

    @Override
    public Set<IBindingRecipe> getRecipes() {
        return recipes;
    }

}
