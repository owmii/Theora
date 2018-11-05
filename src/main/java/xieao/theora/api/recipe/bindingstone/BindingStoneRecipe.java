package xieao.theora.api.recipe.bindingstone;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.api.player.ability.Ability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BindingStoneRecipe implements IBindingStoneRecipe {

    private final List<ItemStack> inputs;
    private final float liquidAmount;
    private final Ability ability;

    public BindingStoneRecipe(Ability resultAbility, float liquidAmount, ItemStack... inputs) {
        this.inputs = Arrays.asList(inputs);
        this.liquidAmount = liquidAmount;
        this.ability = resultAbility;
    }

    @Override
    public boolean matches(List<ItemStack> stacks, float storedLiquid, World world, BlockPos pos) {
        List<ItemStack> itemStacks = new ArrayList<>(this.inputs);
        if (this.liquidAmount <= storedLiquid) {
            if (!stacks.isEmpty()) {
                for (ItemStack stack : stacks) {
                    if (!stack.isEmpty()) {
                        boolean flag = false;
                        for (ItemStack stack1 : itemStacks) {
                            if (stack.isItemEqual(stack1)) {
                                itemStacks.remove(stack1);
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            return false;
                        }
                    }
                }
            }
        }
        return itemStacks.isEmpty();
    }

    @Override
    public List<ItemStack> getInputs() {
        return inputs;
    }

    @Override
    public float getLiquidAmount() {
        return liquidAmount;
    }

    @Override
    public Ability getResultAbility() {
        return ability;
    }
}
