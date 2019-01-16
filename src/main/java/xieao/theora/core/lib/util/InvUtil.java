package xieao.theora.core.lib.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InvUtil {
    public static NBTTagCompound writeItems(NBTTagCompound tag, IInventory inv) {
        return writeItems(tag, inv, true);
    }

    public static NBTTagCompound writeItems(NBTTagCompound tag, IInventory inv, boolean saveEmpty) {
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                itemstack.write(nbttagcompound);
                nbttaglist.add(nbttagcompound);
            }
        }
        if (!nbttaglist.isEmpty() || saveEmpty) {
            tag.setTag("Items", nbttaglist);
        }
        return tag;
    }

    public static void readAllItems(NBTTagCompound tag, IInventory inv) {
        NBTTagList nbttaglist = tag.getList("Items", 10);
        for (int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompound(i);
            int j = nbttagcompound.getByte("Slot") & 255;
            if (j < inv.getSizeInventory()) {
                inv.setInventorySlotContents(j, ItemStack.read(nbttagcompound));
            }
        }
    }
}
