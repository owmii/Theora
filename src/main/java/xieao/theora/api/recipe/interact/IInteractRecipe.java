package xieao.theora.api.recipe.interact;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.IRecipeRegistry;

public interface IInteractRecipe extends IRecipeRegistry.Recipe {
    boolean matches(Liquid liquid, float amount, World world, BlockPos pos);

    Object getResult();

    Object getBlock();

    Liquid getLiquid();

    float getAmount();
}
