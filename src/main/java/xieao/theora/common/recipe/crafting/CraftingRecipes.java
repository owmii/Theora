package xieao.theora.common.recipe.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import xieao.theora.common.block.TheoraBlocks;

public class CraftingRecipes {

    public static void initRecipes() {
        ForgeRegistries.RECIPES.register(new RecipeEmptyLiquidUrn("liquidurn", new ItemStack(TheoraBlocks.LIQUID_URN)));
    }

}
