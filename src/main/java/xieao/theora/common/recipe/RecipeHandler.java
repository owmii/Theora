package xieao.theora.common.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.IRecipeRegistry;
import xieao.theora.api.recipe.binding.IBindingRecipe;
import xieao.theora.api.recipe.binding.IBindingRegistry;
import xieao.theora.api.recipe.cauldron.ICauldronRecipe;
import xieao.theora.api.recipe.cauldron.ICauldronRegistry;
import xieao.theora.api.recipe.fermentingjar.IFermentingRecipe;
import xieao.theora.api.recipe.fermentingjar.IFermentingRegistry;
import xieao.theora.api.recipe.liquidinteract.ILiquidInteractRecipe;
import xieao.theora.api.recipe.liquidinteract.ILiquidInteractRegistry;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeHandler {

    public static final Set<ICauldronRecipe> CAULDRON_RECIPES = new HashSet<>();
    public static final Set<IFermentingRecipe> FERMENTING_RECIPES = new HashSet<>();
    public static final Set<ILiquidInteractRecipe> LIQUID_INTERACT_RECIPES = new HashSet<>();
    public static final Set<IBindingRecipe> BINDING_STONE_RECIPES = new HashSet<>();

    public static void sortRecipes() {
        for (IRecipeRegistry registry : TheoraAPI.INSTANCE.getRecipeRegistries()) {
            if (registry instanceof ICauldronRegistry) {
                ICauldronRegistry cauldronRegistry = (ICauldronRegistry) registry;
                CAULDRON_RECIPES.addAll(cauldronRegistry.getRecipes());
            } else if (registry instanceof IFermentingRegistry) {
                IFermentingRegistry fermentingRegistry = (IFermentingRegistry) registry;
                FERMENTING_RECIPES.addAll(fermentingRegistry.getRecipes());
            } else if (registry instanceof ILiquidInteractRegistry) {
                ILiquidInteractRegistry liquidInteractRegistry = (ILiquidInteractRegistry) registry;
                LIQUID_INTERACT_RECIPES.addAll(liquidInteractRegistry.getRecipes());
            } else if (registry instanceof IBindingRegistry) {
                IBindingRegistry bindingStoneRegistry = (IBindingRegistry) registry;
                BINDING_STONE_RECIPES.addAll(bindingStoneRegistry.getRecipes());
            }
        }
    }

    @Nullable
    public static ICauldronRecipe findCauldronRecipe(IInventory inventory, World world, BlockPos pos) {
        for (ICauldronRecipe recipe : CAULDRON_RECIPES) {
            if (recipe.matches(inventory, world, pos)) {
                return recipe;
            }
        }
        return null;
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
    public static ILiquidInteractRecipe findLiquidInteractRecipe(Liquid liquid, float liquidAmount, World world, BlockPos pos) {
        for (ILiquidInteractRecipe recipe : LIQUID_INTERACT_RECIPES) {
            if (recipe.matches(liquid, liquidAmount, world, pos)) {
                return recipe;
            }
        }
        return null;
    }

    @Nullable
    public static IBindingRecipe findBindingStoneRecipe(List<ItemStack> stacks, float storedLiquid, World world, BlockPos pos) {
        for (IBindingRecipe recipe : BINDING_STONE_RECIPES) {
            if (recipe.matches(stacks, storedLiquid, world, pos)) {
                return recipe;
            }
        }
        return null;
    }
}
