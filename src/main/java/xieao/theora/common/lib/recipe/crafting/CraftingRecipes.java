package xieao.theora.common.lib.recipe.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import xieao.theora.common.block.TheoraBlocks;

public class CraftingRecipes {

    public static void initRecipes() {
        ForgeRegistries.RECIPES.register(new RecipeEmptyLiquidUrn("re-empty_liquid_urn", new ItemStack(TheoraBlocks.LIQUID_URN)));
    }

}
