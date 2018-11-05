package xieao.theora.common.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import xieao.theora.api.player.ability.Ability;
import xieao.theora.api.recipe.bindingstone.BindingStoneRecipe;
import xieao.theora.api.recipe.bindingstone.IBindingStoneRecipe;
import xieao.theora.api.recipe.bindingstone.IBindingStoneRegistry;
import xieao.theora.common.ability.TheoraAbilities;

import java.util.HashSet;
import java.util.Set;

public class BindingStoneRecipes implements IBindingStoneRegistry {

    private final Set<IBindingStoneRecipe> recipes = new HashSet<>();

    @Override
    public void initRecipes() {
        addRecipe(TheoraAbilities.ME_PIG, 0, new ItemStack(Items.CARROT), new ItemStack(Items.APPLE), new ItemStack(Items.CARROT), new ItemStack(Items.CARROT));
    }

    @Override
    public void addRecipe(Ability resultAbility, float liquidAmount, ItemStack... inputs) {
        this.recipes.add(new BindingStoneRecipe(resultAbility, liquidAmount, inputs));
    }

    @Override
    public void addRecipe(IBindingStoneRecipe recipe) {
        this.recipes.add(recipe);
    }

    @Override
    public Set<IBindingStoneRecipe> getRecipes() {
        return recipes;
    }

}
