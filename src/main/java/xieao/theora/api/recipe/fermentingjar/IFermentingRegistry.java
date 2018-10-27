package xieao.theora.api.recipe.fermentingjar;

import net.minecraft.item.ItemStack;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.IRecipeRegistry;

public interface IFermentingRegistry extends IRecipeRegistry<IFermentingRecipe> {

    void addRecipe(ItemStack outputStack, Liquid outputLiquid, ItemStack inputStack);
}
