package xieao.theora.api.recipe.fermentingjar;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xieao.theora.api.liquid.Liquid;

public class FermentingRecipe implements IFermentingRecipe {

    private final ItemStack inputStack;
    private final ItemStack outputStack;
    private final Liquid outputLiquid;

    public FermentingRecipe(ItemStack outputStack, Liquid outputLiquid, ItemStack inputStack) {
        this.inputStack = inputStack;
        this.outputStack = outputStack;
        this.outputLiquid = outputLiquid;
    }

    @Override
    public boolean matches(ItemStack stack, World world, BlockPos pos) {
        return !stack.isEmpty() && stack.isItemEqual(getInputStack());
    }

    @Override
    public ItemStack getInputStack() {
        return inputStack;
    }

    @Override
    public ItemStack getOutputStack() {
        return outputStack;
    }

    @Override
    public Liquid getOutputLiquid() {
        return outputLiquid;
    }
}
