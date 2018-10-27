package xieao.theora.common.block.fermentingjar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemHandlerHelper;
import xieao.theora.api.liquid.Liquid;
import xieao.theora.api.liquid.LiquidContainer;
import xieao.theora.api.liquid.LiquidContainerCapability;
import xieao.theora.api.liquid.LiquidSlot;
import xieao.theora.api.recipe.fermentingjar.IFermentingRecipe;
import xieao.theora.common.block.TileInvBase;
import xieao.theora.common.recipe.RecipeHandler;

import javax.annotation.Nullable;

public class TileFermentingJar extends TileInvBase implements ITickable {

    private final LiquidContainer liquidContainer;

    private final int maxFermentingTime = 1200;
    private int fermenting;

    public TileFermentingJar() {
        this.liquidContainer = new LiquidContainer();
        this.liquidContainer.addLiquidSlots(
                new LiquidSlot(Liquid.EMPTY, false, 400.0F, 0.0F, 20.0F, LiquidSlot.TransferType.ALL)
        );
    }

    @Override
    public void readNBT(NBTTagCompound nbt) {
        super.readNBT(nbt);
        this.liquidContainer.readNBT(nbt);
        this.fermenting = nbt.getInteger("fermenting");
    }

    @Override
    public void writeNBT(NBTTagCompound nbt) {
        super.writeNBT(nbt);
        this.liquidContainer.writeNBT(nbt);
        nbt.setInteger("fermenting", this.fermenting);
    }

    @Override
    public void update() {
        if (isServerWorld()) {
            LiquidSlot liquidSlot = this.liquidContainer.getLiquidSlot(0);
            if (!liquidSlot.isFull() && getStackInSlot(1).isEmpty()) {
                ItemStack stack = getStackInSlot(0);
                if (!stack.isEmpty()) {
                    IFermentingRecipe recipe = RecipeHandler.findFermentingRecipe(stack, getWorld(), getPos());
                    if (recipe != null) {
                        if (this.fermenting++ >= this.maxFermentingTime) {
                            ItemStack outStack = recipe.getOutputStack().copy();
                            outStack.setCount(stack.getCount());
                            setInventorySlotContents(0, ItemStack.EMPTY);
                            setInventorySlotContents(1, outStack);
                            Liquid liquid = recipe.getOutputLiquid();
                            if (!liquid.isEmpty()) {
                                liquidSlot.setLiquid(liquid);
                                float stored = liquidSlot.getStored();
                                liquidSlot.setStored(stored + 10.0F * stack.getCount());
                            }
                            this.fermenting = 0;
                            syncNBTData();
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean insertStack(ItemStack stack, int max, EnumFacing facing) {
        stack = new ItemStack(stack.getItem());
        if (!isServerWorld() && !stack.isEmpty()) {
            ItemStack stack1 = getStackInSlot(0);
            if (stack1.getCount() < 4) {
                stack.shrink(1);
                stack1.grow(1);
                this.fermenting = 0;
                syncNBTData();
                return true;
            }
        }
        return super.insertStack(stack, 1, facing);
    }

    @Override
    public boolean takeStack(EntityPlayer player, EnumFacing facing) {
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = getStackInSlot(i);
            if (!stack.isEmpty()) {
                stack.shrink(1);
                ItemStack copy = stack.copy();
                copy.setCount(1);
                ItemHandlerHelper.giveItemToPlayer(player, copy);
                syncNBTData();
                return true;
            }
        }
        return false;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        super.setInventorySlotContents(index, stack);
        this.fermenting = 0;
    }

    @Override
    public int getSizeInventory() {
        return 2;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == LiquidContainerCapability.CAPABILITY_LIQUID_CONTAINER
                || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == LiquidContainerCapability.CAPABILITY_LIQUID_CONTAINER ? (T) this.liquidContainer
                : super.getCapability(capability, facing);
    }
}
