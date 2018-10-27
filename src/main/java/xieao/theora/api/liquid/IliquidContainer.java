package xieao.theora.api.liquid;

import net.minecraft.nbt.NBTTagCompound;

public interface IliquidContainer {

    LiquidSlot[] getLiquidSlots();

    void setLiquidSlots(LiquidSlot[] liquidSlots);

    LiquidSlot getLiquidSlot(int index);

    void setLiquidSlot(int index, LiquidSlot liquidSlot);

    void readNBT(NBTTagCompound compound);

    void writeNBT(NBTTagCompound compound);

    interface Item extends IliquidContainer {

    }
}
