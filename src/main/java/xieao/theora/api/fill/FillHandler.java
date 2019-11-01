package xieao.theora.api.fill;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class FillHandler {
    protected final Map<String, Slot> slots = new HashMap<>();

    public Map<String, Slot> getSlots() {
        return slots;
    }

    public Slot getSlot(String key) {
        return this.slots.get(key);
    }

    public FillHandler setSlot(String key, Slot slot) {
        this.slots.put(key, slot);
        return this;
    }

    public int slotCount() {
        return this.slots.size();
    }

    public FillHandler addSlot(String key, Fill fill, float capacity, float transferRate, Transfer transfer) {
        return setSlot(key, new Slot(fill, !fill.isEmpty(), capacity, 0, transferRate, transfer));
    }

    public void read(CompoundNBT compound) {
        ListNBT tagList = compound.getList("FillSlots", Constants.NBT.TAG_COMPOUND);
        IntStream.range(0, tagList.size()).mapToObj(tagList::getCompound).forEach(nbt -> {
            Slot slot = new Slot(Fill.EMPTY, false, 0.0F, 0.0F, 0.0F, Transfer.ALL);
            slot.read(nbt.getCompound("Slot"));
            this.slots.put(nbt.getString("SlotName"), slot);
        });
    }

    public CompoundNBT write(CompoundNBT compound) {
        ListNBT tagList = new ListNBT();
        this.slots.forEach((name, slot) -> {
            CompoundNBT nbt = new CompoundNBT();
            nbt.put("Slot", slot.write(new CompoundNBT()));
            nbt.putString("SlotName", name);
            tagList.add(nbt);
        });
        compound.put("FillSlots", tagList);
        return compound;
    }

    public CompoundNBT serialize() {
        return write(new CompoundNBT());
    }

    public static class Item extends FillHandler implements ICapabilityProvider {
        private final LazyOptional<Item> holder = LazyOptional.of(() -> this);
        private final ItemStack stack;

        public Item(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public Item setSlot(String key, Slot slot) {
            super.setSlot(key, slot);
            this.stack.getOrCreateTag().put("FillTag", serialize());
            return this;
        }

        @Override
        public Slot getSlot(String key) {
            CompoundNBT nbt = this.stack.getTag();
            if (nbt != null && nbt.contains("FillTag")) {
                read(nbt.getCompound("FillTag"));
            }
            return super.getSlot(key);
        }

        @Override
        public <T> LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> cap, @Nullable Direction side) {
            return Fill.Cap.HANDLER_ITEM.orEmpty(cap, holder);
        }

        public ItemStack getStack() {
            return stack;
        }
    }

    public static class Slot {
        public static final Slot EMPTY = new Slot(Fill.EMPTY, true, 0.0F, 0.0F, 0.0F, Transfer.ALL);
        private Fill fill;
        private boolean finalFill, changable;
        private float capacity;
        private float stored;
        private float transferRate;
        private Transfer transfer;

        public Slot(Fill fill, boolean finalFill, float capacity, float stored, float transferRate, Transfer transfer) {
            this.fill = fill;
            this.finalFill = finalFill;
            this.changable = !finalFill;
            this.capacity = capacity;
            this.stored = stored;
            this.transferRate = transferRate;
            this.transfer = transfer;
        }

        public void read(CompoundNBT compound) {
            this.fill = Fill.get(compound.getString("FillName"));
            this.finalFill = compound.getBoolean("FinalFill");
            this.changable = !this.finalFill;
            this.capacity = compound.getFloat("Capacity");
            this.stored = compound.getFloat("Stored");
            this.transferRate = compound.getFloat("TransferRate");
            this.transfer = Transfer.values()[compound.getInt("Transfer")];
        }

        public CompoundNBT write(CompoundNBT compound) {
            compound.putString("FillName", this.fill.getString());
            compound.putBoolean("FinalFill", this.finalFill);
            compound.putFloat("Capacity", this.capacity);
            compound.putFloat("Stored", this.stored);
            compound.putFloat("TransferRate", this.transferRate);
            compound.putInt("Transfer", this.transfer.ordinal());
            return compound;
        }

        public Fill getFill() {
            return fill;
        }

        public boolean setFill(Fill fill) {
            if (!this.finalFill) {
                this.fill = fill;
                return true;
            }
            return false;
        }

        public boolean isFinalFill() {
            return finalFill;
        }

        public boolean setFinalFill(boolean finalFill) {
            if (!this.fill.isEmpty() && !this.finalFill) {
                this.finalFill = finalFill;
                return true;
            }
            return false;
        }

        public boolean isChangable() {
            return changable;
        }

        public float getCapacity() {
            return capacity;
        }

        public void setCapacity(float capacity) {
            this.capacity = capacity;
        }

        public float getStored() {
            return stored;
        }

        public void setStored(float stored) {
            this.stored = stored > this.capacity ? this.capacity : stored < 0.0F ? 0.0F : stored;
            if (isEmpty() && !isFinalFill()) {
                setFill(Fill.EMPTY);
            }
        }

        public float getTransferRate() {
            return transferRate;
        }

        public void setTransferRate(float transferRate) {
            this.transferRate = transferRate;
        }

        public Transfer getTransfer() {
            return transfer;
        }

        public void setTransfer(Transfer transfer) {
            this.transfer = transfer;
        }

        public boolean isEmpty() {
            return this.stored <= 0 || this.fill.isEmpty();
        }

        public void setEmpty() {
            setStored(0.0F);
        }

        public boolean isFull() {
            return this.stored >= this.capacity;
        }

        public void setFull() {
            setStored(getCapacity());
        }

        public boolean canReceive(Fill fill) {
            return (!isFull() && fill.equals(this.fill) || isEmpty())
                    && (this.transfer.equals(Transfer.ALL)
                    || this.transfer.equals(Transfer.RECEIVE));
        }

        public boolean canSend(Fill fill) {
            return !isEmpty() && fill.equals(this.fill)
                    && (this.transfer.equals(Transfer.ALL)
                    || this.transfer.equals(Transfer.SEND));
        }

        public void to(Slot other, boolean override, boolean doDrain) {
            if (!isEmpty() && !other.isFull()) {
                if (other.fill.isEmpty()) {
                    other.setFill(getFill());
                }
                if (this.fill.equals(other.fill)) {
                    float amount = Math.min(this.stored, other.capacity - other.stored);
                    float toDrain = Math.min(amount, override ? amount : Math.min(this.transferRate, other.transferRate));
                    if (doDrain) {
                        this.stored -= toDrain;
                        if (isEmpty() && !isFinalFill()) {
                            setFill(Fill.EMPTY);
                        }
                    }
                    other.stored += toDrain;
                }
            }
        }

        public float add(float amount) {
            if (this.fill.isEmpty()) return amount;
            float f = Math.min(amount, this.capacity - this.stored);
            this.stored += f;
            return amount - f;
        }

        public boolean use(float amount) {
            if (amount >= this.stored) {
                this.stored -= amount;
                return true;
            }
            return false;
        }
    }
}
