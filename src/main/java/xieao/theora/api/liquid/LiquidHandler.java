package xieao.theora.api.liquid;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class LiquidHandler {
    protected final Map<String, Slot> slots = new HashMap<>();

    public Map<String, Slot> getSlots() {
        return slots;
    }

    public Slot getSlot(String key) {
        return this.slots.get(key);
    }

    public void setSlot(String key, Slot slot) {
        this.slots.put(key, slot);
    }

    public void addSlot(String key, Liquid liquid, float capacity, float transferRate, Transfer transfer) {
        setSlot(key, new Slot(liquid, !liquid.isEmpty(), capacity, 0, transferRate, transfer));
    }

    public int slotCount() {
        return this.slots.size();
    }

    public void read(NBTTagCompound compound) {
        NBTTagList tagList = compound.getList("LiquidSlots", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            NBTTagCompound nbt = tagList.getCompound(i);
            Slot slot = new Slot(Liquid.EMPTY, false, 0.0F, 0.0F, 0.0F, Transfer.ALL);
            slot.read(nbt.getCompound("Slot"));
            this.slots.put(nbt.getString("SlotName"), slot);
        }
    }

    public NBTTagCompound write(NBTTagCompound compound) {
        NBTTagList tagList = new NBTTagList();
        this.slots.forEach((s, slot) -> {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.put("Slot", slot.write(new NBTTagCompound()));
            nbt.putString("SlotName", s);
            tagList.add(nbt);
        });
        compound.put("LiquidSlots", tagList);
        return compound;
    }

    public NBTTagCompound serialize() {
        return write(new NBTTagCompound());
    }

    public static class Item extends LiquidHandler implements ICapabilityProvider {
        private final LazyOptional<Item> holder = LazyOptional.of(() -> this);
        private final ItemStack stack;

        public Item(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public void setSlot(String key, Slot slot) {
            super.setSlot(key, slot);
            this.stack.getOrCreateTag().put("LiquidTag", write(new NBTTagCompound()));
        }

        @Override
        public Slot getSlot(String key) {
            NBTTagCompound nbt = this.stack.getTag();
            if (nbt != null && nbt.contains("LiquidTag")) {
                read(nbt.getCompound("LiquidTag"));
            }
            return super.getSlot(key);
        }

        @Override
        public <T> LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> cap, @Nullable EnumFacing side) {
            return Liquid.Cap.HANDLER_ITEM.orEmpty(cap, holder);
        }

        public ItemStack getStack() {
            return stack;
        }
    }

    public static class Slot {
        public static final Slot EMPTY = new Slot(Liquid.EMPTY, true, 0.0F, 0.0F, 0.0F, Transfer.ALL);
        private Liquid liquid;
        private boolean finalLiquid, changable;
        private float capacity;
        private float stored;
        private float transferRate;
        private Transfer transfer;

        public Slot(Liquid liquid, boolean finalLiquid, float capacity, float stored, float transferRate, Transfer transfer) {
            this.liquid = liquid;
            this.finalLiquid = finalLiquid;
            this.changable = !finalLiquid;
            this.capacity = capacity;
            this.stored = stored;
            this.transferRate = transferRate;
            this.transfer = transfer;
        }

        public void read(NBTTagCompound compound) {
            this.liquid = Liquid.get(compound.getString("LiquidName"));
            this.finalLiquid = compound.getBoolean("FinalLiquid");
            this.changable = !this.finalLiquid;
            this.capacity = compound.getFloat("Capacity");
            this.stored = compound.getFloat("Stored");
            this.transferRate = compound.getFloat("TransferRate");
            this.transfer = Transfer.values()[compound.getInt("Transfer")];
        }

        public NBTTagCompound write(NBTTagCompound compound) {
            compound.putString("LiquidName", this.liquid.getString());
            compound.putBoolean("FinalLiquid", this.finalLiquid);
            compound.putFloat("Capacity", this.capacity);
            compound.putFloat("Stored", this.stored);
            compound.putFloat("TransferRate", this.transferRate);
            compound.putInt("Transfer", this.transfer.ordinal());
            return compound;
        }

        public Liquid getLiquid() {
            return liquid;
        }

        public boolean setLiquid(Liquid liquid) {
            if (!this.finalLiquid) {
                this.liquid = liquid;
                return true;
            }
            return false;
        }

        public boolean isFinalLiquid() {
            return finalLiquid;
        }

        public boolean setFinalLiquid(boolean finalLiquid) {
            if (!this.liquid.isEmpty() && !this.finalLiquid) {
                this.finalLiquid = finalLiquid;
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
            if (isEmpty() && !isFinalLiquid()) {
                setLiquid(Liquid.EMPTY);
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
            return this.stored <= 0 || this.liquid.isEmpty();
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

        public boolean canReceive(Liquid liquid) {
            return (!isFull() && liquid.equals(this.liquid) || isEmpty())
                    && (this.transfer.equals(Transfer.ALL)
                    || this.transfer.equals(Transfer.RECEIVE));
        }

        public boolean canSend(Liquid liquid) {
            return !isEmpty() && liquid.equals(this.liquid)
                    && (this.transfer.equals(Transfer.ALL)
                    || this.transfer.equals(Transfer.SEND));
        }

        public void to(Slot other, boolean override, boolean doDrain) {
            if (!isEmpty() && !other.isFull()) {
                if (other.liquid.isEmpty()) {
                    other.setLiquid(getLiquid());
                }
                if (this.liquid.equals(other.liquid)) {
                    float amount = Math.min(this.stored, other.capacity - other.stored);
                    float toDrain = Math.min(amount, override ? amount : Math.min(this.transferRate, other.transferRate));
                    if (doDrain) {
                        this.stored -= toDrain;
                        if (isEmpty() && !isFinalLiquid()) {
                            setLiquid(Liquid.EMPTY);
                        }
                    }
                    other.stored += toDrain;
                }
            }
        }

        public float add(float amount) {
            if (this.liquid.isEmpty()) return amount;
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
