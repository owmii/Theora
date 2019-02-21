package xieao.theora.block.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.extensions.IForgeTileEntity;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public interface IInv extends ISidedInventory, IForgeTileEntity {
    default Tile getTile() {
        return (Tile) this;
    }

    @Override
    default int[] getSlotsForFace(EnumFacing enumFacing) {
        return IntStream.range(0, getSizeInventory() - 1).toArray();
    }

    @Override
    default boolean canInsertItem(int index, ItemStack itemStack, @Nullable EnumFacing enumFacing) {
        return isItemValidForSlot(index, itemStack);
    }

    @Override
    default boolean canExtractItem(int index, ItemStack itemStack, EnumFacing enumFacing) {
        return true;
    }

    @Override
    default int getSizeInventory() {
        return getTile().inv.size();
    }

    @Override
    default boolean isEmpty() {
        return getTile().inv.isEmpty();
    }

    @Override
    default ItemStack getStackInSlot(int index) {
        return getTile().inv.get(index);
    }

    @Override
    default ItemStack decrStackSize(int index, int count) {
        ItemStack itemstack = ItemStackHelper.getAndSplit(getTile().inv, index, count);
        if (!itemstack.isEmpty()) {
            markDirty();
        }
        return itemstack;
    }

    @Override
    default ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(getTile().inv, index);
    }

    @Override
    default void setInventorySlotContents(int index, ItemStack itemStack) {
        ItemStack oldStack = getStackInSlot(index);
        getTile().inv.set(index, itemStack);
        if (itemStack.getCount() > this.getInventoryStackLimit()) {
            itemStack.setCount(this.getInventoryStackLimit());
        }
        markDirty();
        if (!oldStack.isItemEqual(itemStack)) {
            onInventoryChanged(index);
        }
    }

    default void onInventoryChanged(int index) {
    }

    @Override
    default int getInventoryStackLimit() {
        return 64;
    }

    @Override
    default boolean isUsableByPlayer(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    default void openInventory(EntityPlayer entityPlayer) {
    }

    @Override
    default void closeInventory(EntityPlayer entityPlayer) {
    }

    @Override
    default boolean isItemValidForSlot(int index, ItemStack itemStack) {
        return true;
    }

    @Override
    default int getField(int id) {
        return 0;
    }

    @Override
    default void setField(int id, int value) {
    }

    @Override
    default int getFieldCount() {
        return 0;
    }

    @Override
    default void clear() {
        for (int i = 0; i < getSizeInventory(); ++i) {
            ItemStack stack = getStackInSlot(i);
            if (!stack.isEmpty()) {
                setInventorySlotContents(i, ItemStack.EMPTY);
            }
        }
    }

    @Override
    default int getHeight() {
        return 0;
    }

    @Override
    default int getWidth() {
        return 0;
    }

    @Override
    default boolean hasCustomName() {
        return getTile().customName != null;
    }

    default void setCustomName(@Nullable ITextComponent name) {
        getTile().customName = name;
    }

    @Override
    default ITextComponent getDisplayName() {
        return getTile().customName != null ? getTile().customName : getName();
    }

    @Nullable
    @Override
    default ITextComponent getCustomName() {
        return getTile().customName;
    }
}
