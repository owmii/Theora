package xieao.theora.common.recipe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.liquidinteract.ILiquidInteractRecipe;
import xieao.theora.api.recipe.liquidinteract.ILiquidInteractRegistry;
import xieao.theora.api.recipe.liquidinteract.LiquidInteractRecipe;
import xieao.theora.common.liquid.TheoraLiquids;

import java.util.HashSet;
import java.util.Set;

public class LiquidInteractRecipes implements ILiquidInteractRegistry {

    private final Set<ILiquidInteractRecipe> recipes = new HashSet<>();

    @Override
    public void initRecipes() {
        addRecipe(Blocks.GLOWSTONE.getDefaultState(), TheoraLiquids.LEQUEN, 100.0F, Blocks.LOG.getDefaultState(), false);
        addRecipe(Blocks.SOUL_SAND.getDefaultState(), TheoraLiquids.LEQUEN, 100.0F, Blocks.GRASS.getDefaultState(), false);
        addRecipe(Blocks.OBSIDIAN.getDefaultState(), TheoraLiquids.LEQUEN, 100.0F, Blocks.GOLD_BLOCK.getDefaultState(), false);
        addRecipe(Blocks.BLACK_SHULKER_BOX.getDefaultState(), TheoraLiquids.LEQUEN, 100.0F, Blocks.STONE.getDefaultState(), false);
        addRecipe(Blocks.COAL_BLOCK.getDefaultState(), TheoraLiquids.LEQUEN, 100.0F, Blocks.PLANKS.getDefaultState(), false);

        addRecipe(Blocks.GLOWSTONE.getDefaultState(), TheoraLiquids.GLIOPHIN, 100.0F, Blocks.LOG.getDefaultState(), false);
        addRecipe(Blocks.SOUL_SAND.getDefaultState(), TheoraLiquids.GLIOPHIN, 100.0F, Blocks.GRASS.getDefaultState(), false);
        addRecipe(Blocks.OBSIDIAN.getDefaultState(), TheoraLiquids.GLIOPHIN, 100.0F, Blocks.GOLD_BLOCK.getDefaultState(), false);
        addRecipe(Blocks.BLACK_SHULKER_BOX.getDefaultState(), TheoraLiquids.GLIOPHIN, 100.0F, Blocks.STONE.getDefaultState(), false);
        addRecipe(Blocks.COAL_BLOCK.getDefaultState(), TheoraLiquids.GLIOPHIN, 100.0F, Blocks.PLANKS.getDefaultState(), false);

    }

    @Override
    public void addRecipe(IBlockState outState, Liquid liquid, float liquidAmount, IBlockState inState, boolean exactState) {
        this.recipes.add(new LiquidInteractRecipe(outState, liquid, liquidAmount, inState, exactState));
    }

    @Override
    public void addRecipe(ILiquidInteractRecipe recipe) {
        this.recipes.add(recipe);
    }

    @Override
    public Set<ILiquidInteractRecipe> getRecipes() {
        return recipes;
    }
}
