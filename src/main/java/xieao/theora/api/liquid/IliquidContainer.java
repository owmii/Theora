package xieao.theora.api.liquid;

import net.minecraft.nbt.NBTTagCompound;

public interface IliquidContainer {

    LiquidSlot[] getLiquidSlots();

    IliquidContainer setLiquidSlots(LiquidSlot[] liquidSlots);

    LiquidSlot getLiquidSlot(int index);

    IliquidContainer setLiquidSlot(int index, LiquidSlot liquidSlot);

    void readNBT(NBTTagCompound compound);

    void writeNBT(NBTTagCompound compound);

    interface Item extends IliquidContainer {

    }
}
