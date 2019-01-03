package xieao.theora.common.item.recipe;

import net.minecraft.item.ItemStack;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.cauldron.CauldronRecipe;
import xieao.theora.api.recipe.cauldron.ICauldronRecipe;
import xieao.theora.api.recipe.cauldron.ICauldronRegistry;
import xieao.theora.common.block.misc.BlockShroom;
import xieao.theora.common.item.TheoraItems;
import xieao.theora.common.liquid.TheoraLiquids;

import java.util.HashSet;
import java.util.Set;

public class CauldronRecipes implements ICauldronRegistry {

    private final Set<ICauldronRecipe> recipes = new HashSet<>();

    @Override
    public void initRecipes() {
        addRecipe(TheoraLiquids.GLIOPHIN, new ItemStack(TheoraItems.SHROOM_BIT, 1, BlockShroom.Type.GLIOPHORUS.ordinal()), new ItemStack(TheoraItems.SHROOM_BIT, 1, BlockShroom.Type.GLIOPHORUS.ordinal()), new ItemStack(TheoraItems.SHROOM_BIT, 1, BlockShroom.Type.GLIOPHORUS.ordinal()), new ItemStack(TheoraItems.SHROOM_BIT, 1, BlockShroom.Type.GLIOPHORUS.ordinal()));
    }

    @Override
    public void addRecipe(Liquid liquid, Object... objects) {
        this.recipes.add(new CauldronRecipe(liquid, objects));
    }

    @Override
    public void addRecipe(ICauldronRecipe recipe) {
        this.recipes.add(recipe);
    }

    @Override
    public Set<ICauldronRecipe> getRecipes() {
        return recipes;
    }
}
