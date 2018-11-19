package xieao.theora.api.liquid;

import net.minecraft.nbt.NBTTagCompound;

public class LiquidSlot {

    private Liquid liquid;
    private boolean finalLiquid;
    private float capacity;
    private float stored;
    private float transfer;
    private TransferType transferType;

    public LiquidSlot(Liquid liquid, boolean finalLiquid, float capacity, float stored, float transfer, TransferType transferType) {
        this.liquid = liquid;
        this.finalLiquid = finalLiquid;
        this.capacity = capacity;
        this.stored = stored;
        this.transfer = transfer;
        this.transferType = transferType;
    }

    public void readNBT(NBTTagCompound compound) {
        this.liquid = Liquid.getLiquid(compound.getString("liquidName"));
        this.finalLiquid = compound.getBoolean("finalLiquid");
        this.capacity = compound.getFloat("capacity");
        this.stored = compound.getFloat("stored");
        this.transfer = compound.getFloat("transfer");
        this.transferType = TransferType.values()[compound.getInteger("transferType")];
    }

    public void writeNBT(NBTTagCompound compound) {
        compound.setString("liquidName", this.liquid.getRegistryString());
        compound.setBoolean("finalLiquid", this.finalLiquid);
        compound.setFloat("capacity", this.capacity);
        compound.setFloat("stored", this.stored);
        compound.setFloat("transfer", this.transfer);
        compound.setInteger("transferType", this.transferType.ordinal());
    }

    public void drain(LiquidSlot other, boolean override, boolean doDrain) {
        if (!isEmpty() && !other.isFull() && this.liquid.equals(other.liquid)) {
            float amount = Math.min(this.stored, other.capacity - other.stored);
            float transfer = override ? amount : Math.min(this.transfer, other.transfer);
            float toDrain = Math.min(amount, transfer);
            if (doDrain) {
                this.stored -= toDrain;
            }
            other.stored += toDrain;
        }
        if (isEmpty() && !hasFinalLiquid()) {
            setLiquid(Liquid.EMPTY);
        }
    }

    public void fill(LiquidSlot other, boolean override) {
        if (!isFull() && !other.isEmpty() && this.liquid.equals(other.liquid)) {
            float amount = Math.min(other.stored, this.capacity - this.stored);
            float transfer = override ? amount : Math.min(other.transfer, this.transfer);
            float toFill = Math.min(amount, transfer);
            this.stored += toFill;
            other.stored -= toFill;
        }
        if (other.isEmpty() && !other.hasFinalLiquid()) {
            other.setLiquid(Liquid.EMPTY);
        }
    }

    public Liquid getLiquid() {
        return liquid;
    }

    public void setLiquid(Liquid liquid) {
        this.liquid = liquid;
    }

    public boolean hasFinalLiquid() {
        return finalLiquid;
    }

    public void setFinalLiquid(boolean finalLiquid) {
        this.finalLiquid = finalLiquid;
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
        this.stored = stored > this.capacity ? this.capacity : stored;
        if (isEmpty() && !hasFinalLiquid()) {
            setLiquid(Liquid.EMPTY);
        }
    }

    public float getTransfer() {
        return transfer;
    }

    public void setTransfer(float transfer) {
        this.transfer = transfer;
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


    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    public boolean liquidEquals(Liquid liquid) {
        return liquid.equals(getLiquid());
    }

    public enum TransferType {
        ALL, SEND, RECEIVE
    }
}
