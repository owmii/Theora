package xieao.theora.api.recipe.cauldron;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.IRecipeRegistry;

import java.util.List;

public interface ICauldronRecipe extends IRecipeRegistry.Entry {

    boolean matches(IInventory inventory, World world, BlockPos pos);

    List<Object> inputs();

    Liquid getLiquid();
}
