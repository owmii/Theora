package xieao.theora.common.block;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.stream.IntStream;

public abstract class TileInvBase extends TileBase implements ISidedInventory {

    public final NonNullList<ItemStack> stacks = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
    private String customName = "";

    @Override
    public void readNBT(NBTTagCompound nbt) {
        super.readNBT(nbt);
        ItemStackHelper.loadAllItems(nbt, this.stacks);
    }

    @Override
    public void writeNBT(NBTTagCompound nbt) {
        super.writeNBT(nbt);
        ItemStackHelper.saveAllItems(nbt, this.stacks);
    }

    public boolean insertStack(ItemStack stack, int max, EnumFacing facing) {
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack1 = stack.copy();
            if (canInsertItem(i, stack, facing)) {
                if (stack1.getCount() >= max) {
                    stack1.setCount(max);
                    stack.shrink(max);
                    setInventorySlotContents(i, stack1);
                    syncNBTData();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean takeStack(EntityPlayer player, EnumFacing facing) {
        for (int i = getSizeInventory() - 1; i >= 0; i--) {
            ItemStack stack = getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (canExtractItem(i, stack, facing)) {
                    ItemHandlerHelper.giveItemToPlayer(player, stack.copy());
                    setInventorySlotContents(i, ItemStack.EMPTY);
                    syncNBTData();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean collectItems(EntityItem entityItem, int amount, int... excludedSlots) {
        for (int i = 0; i < getSizeInventory(); i++) {
            boolean flag = false;
            for (int excludedSlot : excludedSlots) {
                if (i == excludedSlot) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                ItemStack stack = getStackInSlot(i);
                if (stack.isEmpty()) {
                    collectItem(entityItem, amount, i);
                }
            }
        }
        return false;
    }

    public boolean collectItem(EntityItem entityItem, int amount, int slot) {
        ItemStack stack = entityItem.getItem();
        amount = Math.min(stack.getCount(), amount);
        ItemStack copyStack = stack.copy();
        copyStack.setCount(amount);
        if (isItemValidForSlot(slot, copyStack)) {
            setInventorySlotContents(slot, copyStack);
            stack.shrink(amount);
            syncNBTData();
            return true;
        }
        return false;
    }

    public NonNullList<ItemStack> getStacks() {
        return stacks;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing facing) {
        return false;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return IntStream.range(0, getSizeInventory() - 1).toArray();
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing facing) {
        return isItemValidForSlot(index, stack);
    }

    @Override
    public abstract int getSizeInventory();

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : getStacks()) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return getStacks().get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(getStacks(), index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(getStacks(), index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        getStacks().set(index, stack);
        if (stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < getSizeInventory(); i++) {
            setInventorySlotContents(i, ItemStack.EMPTY);
        }
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : getContainerName();
    }

    public String getContainerName() {
        return "";
    }

    @Override
    public boolean hasCustomName() {
        return !this.customName.isEmpty();
    }


    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation(getName());
    }
}
