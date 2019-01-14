package xieao.theora.api.recipe.cauldron;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.recipe.IRecipeRegistry;

import java.util.List;

public interface ICauldronRecipe extends IRecipeRegistry.Recipe {
    <T extends TileEntity & IInventory> boolean matches(T inventory);

    List<Object> inputs();

    Liquid getLiquid();
}
