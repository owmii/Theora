package xieao.theora.core.handler;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.IRecipeRegistry;
import xieao.theora.api.recipe.cauldron.ICauldronRecipe;
import xieao.theora.api.recipe.cauldron.ICauldronRegistry;
import xieao.theora.api.recipe.interact.IInteractRecipe;
import xieao.theora.api.recipe.interact.IInteractRegistry;
import xieao.theora.core.lib.annotation.PostLoad;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

@PostLoad
public class RecipeSorter {
    public static final Set<IInteractRecipe> INTERACT_RECIPES = new HashSet<>();
    public static final Set<ICauldronRecipe> CAULDRON_RECIPES = new HashSet<>();

    static {
        for (IRecipeRegistry registry : TheoraAPI.API.getRecipeRegistries()) {
            if (registry instanceof IInteractRegistry) {
                IInteractRegistry reg = (IInteractRegistry) registry;
                INTERACT_RECIPES.addAll(reg.getRecipes());
            } else if (registry instanceof ICauldronRegistry) {
                ICauldronRegistry reg = (ICauldronRegistry) registry;
                CAULDRON_RECIPES.addAll(reg.getRecipes());
            }
        }
    }

    @Nullable
    public static IInteractRecipe getInteractRecipe(Liquid liquid, float amount, World world, BlockPos pos) {
        for (IInteractRecipe recipe : INTERACT_RECIPES) {
            if (recipe.matches(liquid, amount, world, pos)) {
                return recipe;
            }
        }
        return null;
    }

    @Nullable
    public static <T extends TileEntity & IInventory> ICauldronRecipe getCauldronRecipe(T inventory) {
        for (ICauldronRecipe recipe : CAULDRON_RECIPES) {
            if (recipe.matches(inventory)) {
                return recipe;
            }
        }
        return null;
    }
}
