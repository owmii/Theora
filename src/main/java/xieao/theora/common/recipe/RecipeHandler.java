package xieao.theora.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.recipe.IRecipeRegistry;
import xieao.theora.api.recipe.bindingstone.IBindingStoneRecipe;
import xieao.theora.api.recipe.bindingstone.IBindingStoneRegistry;
import xieao.theora.api.recipe.fermentingjar.IFermentingRecipe;
import xieao.theora.api.recipe.fermentingjar.IFermentingRegistry;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeHandler {

    public static final Set<IFermentingRecipe> FERMENTING_RECIPES = new HashSet<>();
    public static final Set<IBindingStoneRecipe> BINDING_STONE_RECIPES = new HashSet<>();

    public static void initRecipes() {
        for (IRecipeRegistry registry : TheoraAPI.INSTANCE.getRecipeRegistries()) {
            if (registry instanceof IFermentingRegistry) {
                IFermentingRegistry fermentingRegistry = (IFermentingRegistry) registry;
                FERMENTING_RECIPES.addAll(fermentingRegistry.getRecipes());
            } else if (registry instanceof IBindingStoneRegistry) {
                IBindingStoneRegistry bindingStoneRegistry = (IBindingStoneRegistry) registry;
                BINDING_STONE_RECIPES.addAll(bindingStoneRegistry.getRecipes());
            }
        }
    }

    @Nullable
    public static IFermentingRecipe findFermentingRecipe(ItemStack stack, World world, BlockPos pos) {
        for (IFermentingRecipe recipe : FERMENTING_RECIPES) {
            if (recipe.matches(stack, world, pos)) {
                return recipe;
            }
        }
        return null;
    }

    @Nullable
    public static IBindingStoneRecipe findBindingStoneRecipe(List<ItemStack> stacks, float storedLiquid, World world, BlockPos pos) {
        for (IBindingStoneRecipe recipe : BINDING_STONE_RECIPES) {
            if (recipe.matches(stacks, storedLiquid, world, pos)) {
                return recipe;
            }
        }
        return null;
    }
}
