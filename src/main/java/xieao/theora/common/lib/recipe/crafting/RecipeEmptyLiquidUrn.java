package xieao.theora.common.lib.recipe.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import xieao.theora.Theora;
import xieao.theora.api.liquid.LiquidContainer;
import xieao.theora.api.liquid.LiquidSlot;
import xieao.theora.common.block.BlockBase;
import xieao.theora.common.lib.helper.NBTHelper;

public class RecipeEmptyLiquidUrn extends ShapelessOreRecipe {


    public RecipeEmptyLiquidUrn(String location, ItemStack stack) {
        super(Theora.location(location), stack, stack);
        setRegistryName(location);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting var1) {
        ItemStack outStack = this.output.copy();
        for (int i = 0; i < var1.getSizeInventory(); i++) {
            ItemStack inStack = var1.getStackInSlot(i);
            if (!inStack.isEmpty()) {
                LiquidContainer liquidContainer = new LiquidContainer();
                liquidContainer.addLiquidSlots(LiquidContainer.EMPTY_SLOT);
                if (NBTHelper.hasNBT(inStack) && NBTHelper.hasKey(inStack, BlockBase.TAG_TILE_DATA, Constants.NBT.TAG_COMPOUND)) {
                    liquidContainer.readNBT(NBTHelper.getCompoundTag(inStack, BlockBase.TAG_TILE_DATA));
                    LiquidSlot liquidSlot = liquidContainer.getLiquidSlot(0);
                    liquidSlot.setEmpty();
                    NBTTagCompound compound = new NBTTagCompound();
                    liquidContainer.writeNBT(compound);
                    NBTHelper.setTag(outStack, BlockBase.TAG_TILE_DATA, compound);
                }
            }
        }
        return outStack;
    }
}
