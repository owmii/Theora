package xieao.theora.api.liquid;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.Constants;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.registry.RegistryEntry;

import javax.annotation.Nullable;
import java.util.*;

public class Liquid extends RegistryEntry {
    public static final Map<ResourceLocation, Liquid> REGISTRY;
    public static final Liquid EMPTY;

    private int darkColor;
    private int brightColor;

    static {
        REGISTRY = new HashMap<>();
        EMPTY = register(TheoraAPI.EMPTY, 0, 0);
    }

    public static Liquid register(String name, int darkColor, int brightColor) {
        Liquid liquid = new Liquid();
        liquid.setRegistryName(name);
        liquid.darkColor = darkColor;
        liquid.brightColor = brightColor;
        REGISTRY.put(liquid.getRegistryName(), liquid);
        return liquid;
    }

    public static Liquid read(String key, NBTTagCompound compound) {
        NBTTagCompound nbt = compound.getCompound(key);
        return getLiquid(nbt.getString("regName"));
    }

    public static Liquid read(NBTTagCompound compound) {
        return getLiquid(compound.getString("regName"));
    }

    public static void write(String key, Liquid liquid, NBTTagCompound compound) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("regName", liquid.getRegistryString());
        compound.setTag(key, nbt);
    }

    public static Liquid getLiquid(String name) {
        Liquid liquid = REGISTRY.get(new ResourceLocation(name));
        return liquid == null ? EMPTY : liquid;
    }

    public static void write(Liquid liquid, NBTTagCompound compound) {
        compound.setString("regName", liquid.getRegistryString());
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public int getDarkColor() {
        return darkColor;
    }

    public int getBrightColor() {
        return brightColor;
    }


    public static class Handler {
        public static final Slot SLOT_EMPTY = new Slot(Liquid.EMPTY, true, 0.0F, 0.0F, 0.0F, TransferType.ALL);
        protected Slot[] slots = new Slot[0];

        public Slot[] getSlots() {
            return slots;
        }

        public void setSlots(Slot[] slots) {
            this.slots = slots;
        }

        public Slot getSlot(int index) {
            return this.slots[index];
        }

        public void setLiquidSlot(int index, Slot slot) {
            this.slots[index] = slot;
        }

        public void addSlot(float capacity, float transferRate, TransferType transferType) {
            addSlots(new Slot(Liquid.EMPTY, false, capacity, 0, transferRate, transferType));
        }

        public void addSlots(Slot slot) {
            List<Slot> list = new ArrayList<>(Arrays.asList(this.slots));
            list.add(slot);
            this.slots = list.toArray(new Slot[0]);
        }

        public void read(NBTTagCompound compound) {
            NBTTagList tagList = compound.getList("liquidSlots", Constants.NBT.TAG_COMPOUND);
            this.slots = new Slot[tagList.size()];
            for (int i = 0; i < tagList.size(); i++) {
                NBTTagCompound nbt = tagList.getCompound(i);
                this.slots[i] = new Slot(Liquid.EMPTY, false, 0.0F, 0.0F, 0.0F, TransferType.ALL);
                this.slots[i].read(nbt);
            }
        }

        public NBTTagCompound write(NBTTagCompound compound) {
            NBTTagList tagList = new NBTTagList();
            for (Slot slot : this.slots) {
                NBTTagCompound nbt = new NBTTagCompound();
                slot.write(nbt);
                tagList.add(nbt);
            }
            compound.setTag("liquidSlots", tagList);
            return compound;
        }

        public static class Item extends Handler implements ICapabilityProvider {
            private final OptionalCapabilityInstance<Item> holder = OptionalCapabilityInstance.of(() -> this);
            private final ItemStack stack;

            public Item(ItemStack stack) {
                this.stack = stack;
            }

            @Override
            public Slot getSlot(int index) {
                NBTTagCompound nbt = this.stack.getTag();
                if (nbt != null && nbt.hasKey("liquidTag")) {
                    read(nbt.getCompound("liquidTag"));
                }
                return super.getSlot(index);
            }

            @Override
            public void setLiquidSlot(int index, Slot slot) {
                super.setLiquidSlot(index, slot);
                NBTTagCompound nbt = new NBTTagCompound();
                this.stack.getOrCreateTag().setTag("liquidTag", write(nbt));
            }

            @Override
            public <T> OptionalCapabilityInstance<T> getCapability(Capability<T> cap, @Nullable EnumFacing side) {
                return OptionalCapabilityInstance.orEmpty(cap, Cap.LIQUID_HANDLER_ITEM, holder);
            }

            public ItemStack getStack() {
                return stack;
            }
        }
    }

    public static class Slot {
        private Liquid liquid;
        private boolean finalLiquid;
        private float capacity;
        private float stored;
        private float transferRate;
        private TransferType transferType;

        public Slot(Liquid liquid, boolean finalLiquid, float capacity, float stored, float transferRate, TransferType transferType) {
            this.liquid = liquid;
            this.finalLiquid = finalLiquid;
            this.capacity = capacity;
            this.stored = stored;
            this.transferRate = transferRate;
            this.transferType = transferType;
        }

        public void read(NBTTagCompound compound) {
            this.liquid = Liquid.getLiquid(compound.getString("liquidName"));
            this.finalLiquid = compound.getBoolean("finalLiquid");
            this.capacity = compound.getFloat("capacity");
            this.stored = compound.getFloat("stored");
            this.transferRate = compound.getFloat("transferType");
            this.transferType = TransferType.values()[compound.getInt("transferType")];
        }

        public NBTTagCompound write(NBTTagCompound compound) {
            compound.setString("liquidName", this.liquid.getRegistryString());
            compound.setBoolean("finalLiquid", this.finalLiquid);
            compound.setFloat("capacity", this.capacity);
            compound.setFloat("stored", this.stored);
            compound.setFloat("transferType", this.transferRate);
            compound.setInt("transferType", this.transferType.ordinal());
            return compound;
        }

        public Liquid getLiquid() {
            return liquid;
        }

        public boolean setLiquid(Liquid liquid) {
            if (!this.finalLiquid) {
                this.liquid = liquid;
                if (liquid.isEmpty()) {
                    setEmpty();
                }
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

        public TransferType getTransferType() {
            return transferType;
        }

        public void setTransferType(TransferType transferType) {
            this.transferType = transferType;
        }

        public boolean isEmpty() {
            return this.stored <= 0;
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

    public static class Cap {
        @CapabilityInject(Handler.class)
        @SuppressWarnings("ConstantConditions")
        public static Capability<Handler> LIQUID_HANDLER = null;
        @CapabilityInject(Handler.Item.class)
        @SuppressWarnings("ConstantConditions")
        public static Capability<Handler.Item> LIQUID_HANDLER_ITEM = null;

        public static void register() {
            CapabilityManager.INSTANCE.register(Handler.class, new Storage<>(), Handler::new);
            CapabilityManager.INSTANCE.register(Handler.Item.class, new Storage<>(), () -> new Handler.Item(ItemStack.EMPTY));
        }

        private static class Storage<T extends Handler> implements Capability.IStorage<T> {

            @Nullable
            @Override
            public INBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
                return null;
            }

            @Override
            public void readNBT(Capability<T> capability, T instance, EnumFacing side, INBTBase nbt) {

            }
        }
    }
}