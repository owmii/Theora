package xieao.theora.common.recipe;

import net.minecraft.item.ItemStack;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.fermentingjar.FermentingRecipe;
import xieao.theora.api.recipe.fermentingjar.IFermentingRecipe;
import xieao.theora.api.recipe.fermentingjar.IFermentingRegistry;
import xieao.theora.common.block.TheoraBlocks;
import xieao.theora.common.item.TheoraItems;

import java.util.HashSet;
import java.util.Set;

public class FermentingRecipes implements IFermentingRegistry {

    private Set<IFermentingRecipe> fermentingRecipes = new HashSet<>();

    @Override
    public void initRecipes() {
        addRecipe(new ItemStack(TheoraItems.OOZE), Liquid.EMPTY, new ItemStack(TheoraBlocks.SHROOM));
    }

    @Override
    public void addRecipe(ItemStack outputStack, Liquid outputLiquid, ItemStack inputStack) {
        this.fermentingRecipes.add(new FermentingRecipe(outputStack, outputLiquid, inputStack));
    }

    @Override
    public Set<IFermentingRecipe> getRecipes() {
        return fermentingRecipes;
    }

    @Override
    public void addRecipe(IFermentingRecipe recipe) {
        this.fermentingRecipes.add(recipe);
    }
}
