package xieao.theora.api.recipe.bindingstone;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.api.player.ability.Ability;
import xieao.theora.api.recipe.IRecipeRegistry;

import java.util.List;

public interface IBindingStoneRecipe extends IRecipeRegistry.Entry {

    boolean matches(List<ItemStack> stacks, int storedLiquid, World world, BlockPos pos);

    List<ItemStack> getInputs();

    float getLiquidAmount();

    Ability getResultAbility();
}
