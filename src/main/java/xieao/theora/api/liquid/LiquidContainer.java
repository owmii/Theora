package xieao.theora.api.liquid;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LiquidContainer implements IliquidContainer {

    private LiquidSlot[] liquidSlots = new LiquidSlot[0];

    @Override
    public LiquidSlot[] getLiquidSlots() {
        return liquidSlots;
    }

    @Override
    public void setLiquidSlots(LiquidSlot[] liquidSlots) {
        this.liquidSlots = liquidSlots;
    }

    @Override
    public LiquidSlot getLiquidSlot(int index) {
        return this.liquidSlots[index];
    }

    @Override
    public void setLiquidSlot(int index, LiquidSlot liquidSlot) {
        this.liquidSlots[index] = liquidSlot;
    }

    public void addLiquidSlots(LiquidSlot liquidSlot, int count) {
        LiquidSlot[] slots = new LiquidSlot[count];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = liquidSlot;
        }
        this.liquidSlots = slots;
    }

    public void addLiquidSlots(LiquidSlot... liquidSlots) {
        List<LiquidSlot> slots = new ArrayList<>(Arrays.asList(this.liquidSlots));
        slots.addAll(Arrays.asList(liquidSlots));
        this.liquidSlots = slots.toArray(this.liquidSlots);
    }

    @Override
    public void readNBT(NBTTagCompound compound) {
        NBTTagList tagList = compound.getTagList("tagList", Constants.NBT.TAG_COMPOUND);
        this.liquidSlots = new LiquidSlot[tagList.tagCount()];
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound nbt = tagList.getCompoundTagAt(i);
            this.liquidSlots[i] = new LiquidSlot(Liquid.EMPTY, false, 0.0F, 0.0F, 0.0F, LiquidSlot.TransferType.ALL);
            this.liquidSlots[i].readNBT(nbt);
        }
    }

    @Override
    public void writeNBT(NBTTagCompound compound) {
        NBTTagList tagList = new NBTTagList();
        for (LiquidSlot liquidTank : this.liquidSlots) {
            NBTTagCompound nbt = new NBTTagCompound();
            liquidTank.writeNBT(nbt);
            tagList.appendTag(nbt);
        }
        compound.setTag("tagList", tagList);
    }

    public static class Item extends LiquidContainer implements IliquidContainer.Item, ICapabilityProvider {

        private final ItemStack itemStack;

        public Item(ItemStack container) {
            this.itemStack = container;
        }

        @Override
        public LiquidSlot getLiquidSlot(int index) {
            NBTTagCompound nbt = this.itemStack.getTagCompound();
            if (nbt != null && nbt.hasKey("liquidSlots")) {
                NBTTagCompound tagCompound = nbt.getCompoundTag("liquidSlots");
                readNBT(tagCompound);
            }
            return super.getLiquidSlot(index);
        }

        @Override
        public void setLiquidSlot(int index, LiquidSlot liquidSlot) {
            super.setLiquidSlot(index, liquidSlot);
            NBTTagCompound nbt = new NBTTagCompound();
            writeNBT(nbt);
            if (this.itemStack.getTagCompound() == null) {
                this.itemStack.setTagCompound(new NBTTagCompound());
            }
            this.itemStack.getTagCompound().setTag("liquidSlots", nbt);
        }

        public ItemStack getItemStack() {
            return itemStack;
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == LiquidContainerCapability.CAPABILITY_LIQUID_CONTAINER_ITEM;
        }

        @SuppressWarnings("unchecked")
        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return capability == LiquidContainerCapability.CAPABILITY_LIQUID_CONTAINER_ITEM ? (T) this : null;
        }
    }
}
